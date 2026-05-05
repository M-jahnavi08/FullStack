package com.jobportal.controller;

import com.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private JobService jobService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("jobs", jobService.getAllJobs());
        return "home";
    }

    @GetMapping("/jobs")
    public String listJobs(Model model, @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("jobs", jobService.searchJobs(keyword));
        } else {
            model.addAttribute("jobs", jobService.getAllJobs());
        }
        return "jobs";
    }

    @GetMapping("/job-details/{id}")
    public String jobDetails(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "job-details";
    }
}
