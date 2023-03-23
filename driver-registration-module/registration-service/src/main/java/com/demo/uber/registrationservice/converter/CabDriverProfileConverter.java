package com.demo.uber.registrationservice.converter;

import com.demo.uber.helperservice.converter.GenericConverter;
import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.model.SupportedCountry;
import com.demo.uber.helperservice.model.SupportedRegion;
import com.demo.uber.registrationservice.dto.CabDriverProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CabDriverProfileConverter implements GenericConverter<CabDriverProfile, CabDriverProfileRequest> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CabDriverProfile createFromDto(CabDriverProfileRequest dto) {
        return CabDriverProfile.builder()
                .emailId(dto.getEmailId())
                .phoneNumber(dto.getPhoneNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .licenseNumber(dto.getLicenseNumber())
                .region(SupportedRegion.valueOf(dto.getRegion()))
                .country(SupportedCountry.valueOf(dto.getCountry()))
                .build();
    }

    @Override
    public CabDriverProfileRequest createFromEntity(CabDriverProfile entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CabDriverProfile updateEntity(CabDriverProfile entity, CabDriverProfileRequest dto) {
        throw new UnsupportedOperationException();
    }
}
