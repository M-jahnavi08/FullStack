package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.security.CustomUserDetails;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("jobs", jobService.getJobsByEmployer(userDetails.getUser().getId()));
        return "employer/dashboard";
    }

    @GetMapping("/job/post")
    public String postJobPage(Model model) {
        model.addAttribute("job", new Job());
        return "employer/post-job";
    }

    @PostMapping("/job/save")
    public String saveJob(@ModelAttribute Job job, @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        job.setEmployer(userDetails.getUser());
        jobService.saveJob(job);
        redirectAttributes.addFlashAttribute("success", "Job posted successfully!");
        return "redirect:/employer/manage-jobs";
    }

    @GetMapping("/manage-jobs")
    public String manageJobs(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("jobs", jobService.getJobsByEmployer(userDetails.getUser().getId()));
        return "employer/manage-jobs";
    }

    @GetMapping("/job/delete/{id}")
    public String deleteJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        jobService.deleteJob(id);
        redirectAttributes.addFlashAttribute("success", "Job deleted successfully!");
        return "redirect:/employer/manage-jobs";
    }

    @GetMapping("/job/{jobId}/applicants")
    public String viewApplicants(@PathVariable Long jobId, Model model) {
        model.addAttribute("job", jobService.getJobById(jobId));
        model.addAttribute("applications", applicationService.getApplicationsByJob(jobId));
        return "employer/applicants";
    }

    @PostMapping("/application/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status, @RequestParam Long jobId) {
        applicationService.updateApplicationStatus(id, status);
        return "redirect:/employer/job/" + jobId + "/applicants";
    }
}
