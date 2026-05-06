package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsByStudent(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    public List<Application> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public boolean hasAlreadyApplied(Long studentId, Long jobId) {
        return applicationRepository.existsByStudentIdAndJobId(studentId, jobId);
    }

    public void updateApplicationStatus(Long id, String status) {
        Application app = applicationRepository.findById(id).orElse(null);
        if (app != null) {
            app.setStatus(status);
            applicationRepository.save(app);
        }
    }
}
