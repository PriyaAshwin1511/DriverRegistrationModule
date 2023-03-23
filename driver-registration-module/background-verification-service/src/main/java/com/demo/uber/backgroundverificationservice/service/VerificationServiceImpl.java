package com.demo.uber.backgroundverificationservice.service;

import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.model.OnboardingStatus;
import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final CabDriverProfileRepository profileRepository;
    @Override
    public void verifyCabDriverProfile(long driverId) {
        Optional<CabDriverProfile> cabDriverProfiles = profileRepository.findById(driverId);
        if(!cabDriverProfiles.isPresent()) {
            throw new RuntimeException("Invalid cab driver");
        }
        CabDriverProfile cabDriverProfile = cabDriverProfiles.get();
        cabDriverProfile.setOnboardingStatus(OnboardingStatus.BACKGROUND_VERIFICATION_INPROGRESS);
        profileRepository.save(cabDriverProfile);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
           System.out.println("Error waiting for 5 seconds");
        }
        cabDriverProfile.setOnboardingStatus(OnboardingStatus.BACKGROUND_VERIFICATION_COMPLETED);
        profileRepository.save(cabDriverProfile);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting for 5 seconds");
        }
        cabDriverProfile.setOnboardingStatus(OnboardingStatus.DEVICE_SHIPPED);
        profileRepository.save(cabDriverProfile);
    }
}
