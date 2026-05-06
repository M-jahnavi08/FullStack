package com.jobportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @GetMapping("/ask")
    public String ask(@RequestParam String message) {
        String msg = message.toLowerCase();
        
        if (msg.contains("apply") || msg.contains("how to apply")) {
            return "To apply for a job, you first need to register as a Student and log in. Once logged in, browse the 'Find Jobs' page, click on a job, and hit the 'Apply Now' button.";
        } else if (msg.contains("post") || msg.contains("create a job")) {
            return "To post a job, you must register as an Employer. After logging in, go to your Employer Dashboard and click on 'Post a New Job'.";
        } else if (msg.contains("register") || msg.contains("sign up")) {
            return "Click the 'Register' button in the top navigation bar. You can choose to register as a Student (to apply for jobs) or an Employer (to post jobs).";
        } else if (msg.contains("password") || msg.contains("forgot")) {
            return "Currently, if you forget your password, please contact the administrator to reset it.";
        } else if (msg.contains("resume") || msg.contains("cv")) {
            return "Students can upload their resumes in their Dashboard by navigating to 'Update Profile' and attaching a PDF file.";
        } else if (msg.contains("contact") || msg.contains("support")) {
            return "You can reach our support team at support@jobportal.com or call 1-800-JOB-PORTAL.";
        } else {
            return "I'm a simple assistant. You can ask me about 'how to apply', 'posting a job', 'registration', or 'uploading a resume'.";
        }
    }
}
