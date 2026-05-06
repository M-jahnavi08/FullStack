package com.jobportal.config;

import com.jobportal.entity.Job;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.findAllUsers().isEmpty()) {
            
            // 1 Admin
            User admin = User.builder()
                    .fullName("System Admin")
                    .email("admin@jobportal.com")
                    .password("admin123")
                    .role(Role.ADMIN)
                    .build();
            userService.saveUser(admin);

            // 2 Employers
            User employer1 = User.builder()
                    .fullName("TechCorp Inc")
                    .email("employer1@techcorp.com")
                    .password("emp123")
                    .role(Role.EMPLOYER)
                    .build();
            userService.saveUser(employer1);

            User employer2 = User.builder()
                    .fullName("WebSolutions Ltd")
                    .email("employer2@websolutions.com")
                    .password("emp123")
                    .role(Role.EMPLOYER)
                    .build();
            userService.saveUser(employer2);

            // 5 Students
            for (int i = 1; i <= 5; i++) {
                User student = User.builder()
                        .fullName("Student " + i)
                        .email("student" + i + "@gmail.com")
                        .password("pass123")
                        .role(Role.STUDENT)
                        .skills("Java, Spring, SQL")
                        .location("New York")
                        .build();
                userService.saveUser(student);
            }

            // 10 Jobs
            for (int i = 1; i <= 5; i++) {
                Job job = Job.builder()
                        .title("Software Engineer " + i)
                        .companyName("TechCorp Inc")
                        .description("Looking for a skilled Java developer. Required experience " + i + " years.")
                        .skillsRequired("Java, Spring Boot, MySQL")
                        .location("Remote")
                        .salary("$" + (70000 + (i * 10000)))
                        .experienceRequired(i + " Years")
                        .category("IT / Software")
                        .employer(employer1)
                        .build();
                jobService.saveJob(job);
            }

            for (int i = 6; i <= 10; i++) {
                Job job = Job.builder()
                        .title("Frontend Developer " + i)
                        .companyName("WebSolutions Ltd")
                        .description("Looking for a talented frontend dev with React/Angular skills.")
                        .skillsRequired("HTML, CSS, JavaScript, React")
                        .location("New York")
                        .salary("$" + (60000 + (i * 5000)))
                        .experienceRequired("2 Years")
                        .category("IT / Web")
                        .employer(employer2)
                        .build();
                jobService.saveJob(job);
            }
            
            System.out.println("Sample data seeded successfully!");
        }
    }
}
