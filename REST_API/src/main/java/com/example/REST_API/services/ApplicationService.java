package com.example.REST_API.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.REST_API.models.Application;
import com.example.REST_API.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application createApplication(Application application) {
        application.setCreatedOn(LocalDateTime.now());
        application.setModifiedOn(LocalDateTime.now());
        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    public Optional<Application> updateApplication(Long id, Application data) {
        Optional<Application> existingOpt = applicationRepository.findById(id);
        if (existingOpt.isPresent()) {
            Application existing = existingOpt.get();
            existing.setProductType(data.getProductType());
            existing.setProductProgram(data.getProductProgram());
            existing.setCardType(data.getCardType());
            existing.setCampaignCode(data.getCampaignCode());
            existing.setIsVip(data.getIsVip());
            existing.setAppStatus(data.getAppStatus());
            existing.setModifiedOn(LocalDateTime.now());
            applicationRepository.save(existing);
            return Optional.of(existing);
        }
        return Optional.empty();
    }

    public boolean deleteApplication(Long id) {
        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
