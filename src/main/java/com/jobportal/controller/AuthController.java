package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.service.EmailService;
import com.jobportal.service.OtpService;
import com.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email already exists!");
            return "redirect:/register";
        }
        
        user.setVerified(false); // Default to unverified
        userService.saveUser(user);
        
        // Generate OTP
        String otp = otpService.generateOtp(user.getEmail());
        
        // Send Real Email
        emailService.sendOtpEmail(user.getEmail(), otp);
        
        System.out.println("\n\n=======================================================");
        System.out.println("✉️  [OTP SERVER] - REAL EMAIL ATTEMPTED & OTP GENERATED");
        System.out.println("To: " + user.getEmail());
        System.out.println("Your 6-digit OTP is: " + otp);
        System.out.println("=======================================================\n\n");

        redirectAttributes.addAttribute("email", user.getEmail());
        return "redirect:/verify-otp";
    }

    @GetMapping("/verify-otp")
    public String verifyOtpPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, RedirectAttributes redirectAttributes) {
        if (otpService.validateOtp(email, otp)) {
            Optional<User> userOpt = userService.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setVerified(true);
                userService.updateUserProfile(user); // Save verified state
                redirectAttributes.addFlashAttribute("success", "Email verified successfully! You can now login.");
                return "redirect:/login";
            }
        }
        
        redirectAttributes.addFlashAttribute("error", "Invalid or expired OTP. Please try again.");
        redirectAttributes.addAttribute("email", email);
        return "redirect:/verify-otp";
    }
}
