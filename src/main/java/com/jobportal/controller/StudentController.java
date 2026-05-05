package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.security.CustomUserDetails;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.FileStorageService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("applications", applicationService.getApplicationsByStudent(userDetails.getUser().getId()));
        return "student/dashboard";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userService.getUserById(userDetails.getUser().getId()));
        return "student/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updatedUser, @RequestParam("resumeFile") MultipartFile file, 
                              @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        User existingUser = userService.getUserById(userDetails.getUser().getId());
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setLocation(updatedUser.getLocation());
        existingUser.setSkills(updatedUser.getSkills());
        existingUser.setExperience(updatedUser.getExperience());

        if (!file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            existingUser.setResumePath(fileName);
        }

        userService.updateUserProfile(existingUser);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/student/profile";
    }

    @PostMapping("/apply/{jobId}")
    public String applyJob(@PathVariable Long jobId, @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        User student = userDetails.getUser();
        
        if (applicationService.hasAlreadyApplied(student.getId(), jobId)) {
            redirectAttributes.addFlashAttribute("error", "You have already applied for this job.");
            return "redirect:/job-details/" + jobId;
        }

        Job job = jobService.getJobById(jobId);
        Application app = new Application();
        app.setStudent(student);
        app.setJob(job);
        app.setStatus("APPLIED");
        
        applicationService.saveApplication(app);
        redirectAttributes.addFlashAttribute("success", "Successfully applied for the job!");
        
        return "redirect:/student/applications";
    }

    @GetMapping("/applications")
    public String myApplications(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("applications", applicationService.getApplicationsByStudent(userDetails.getUser().getId()));
        return "student/applications";
    }
}
