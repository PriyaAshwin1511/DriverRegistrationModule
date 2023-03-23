package com.demo.uber.registrationservice.service;

import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.model.OnboardingStatus;
import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import com.demo.uber.registrationservice.converter.CabDriverProfileConverter;
import com.demo.uber.registrationservice.dto.CabDriverProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService{

    private final CabDriverProfileConverter converter;
    private final CabDriverProfileRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public CabDriverProfile registerDriver(CabDriverProfileRequest request){
        /**
         * Region and country should be supported ones.
         */
        CabDriverProfile cabDriverProfile = converter.createFromDto(request);
        cabDriverProfile.setOnboardingStatus(OnboardingStatus.PROFILE_INFO_ADDED);
        cabDriverProfile =  repository.save(cabDriverProfile);
        return cabDriverProfile;
    }
}
