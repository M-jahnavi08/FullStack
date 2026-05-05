package com.jobportal.controller;

import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.findAllUsers().size());
        model.addAttribute("totalJobs", jobService.getAllJobs().size());
        model.addAttribute("totalApplications", applicationService.getAllApplications().size());
        return "admin/dashboard";
    }

    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/manage-users";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        return "redirect:/admin/manage-users";
    }

    @GetMapping("/manage-jobs")
    public String manageJobs(Model model) {
        model.addAttribute("jobs", jobService.getAllJobs());
        return "admin/manage-jobs";
    }
    
    @GetMapping("/job/delete/{id}")
    public String deleteJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        jobService.deleteJob(id);
        redirectAttributes.addFlashAttribute("success", "Job deleted successfully!");
        return "redirect:/admin/manage-jobs";
    }
}
