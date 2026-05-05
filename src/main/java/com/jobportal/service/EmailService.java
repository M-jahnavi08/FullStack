package com.jobportal.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Verify your Job Portal Account");
            
            String htmlContent = "<h2>Welcome to Job Portal!</h2>"
                    + "<p>Thank you for registering. Please use the following One-Time Password (OTP) to verify your email address:</p>"
                    + "<h1 style='color: #0d6efd; letter-spacing: 5px; padding: 10px; background-color: #f8f9fa; border-radius: 5px; display: inline-block;'>" + otp + "</h1>"
                    + "<p>If you did not request this, please ignore this email.</p>"
                    + "<p>Best regards,<br>The Job Portal Team</p>";
            
            helper.setText(htmlContent, true); // true indicates it's an HTML email

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + toEmail);
        }
    }
}
