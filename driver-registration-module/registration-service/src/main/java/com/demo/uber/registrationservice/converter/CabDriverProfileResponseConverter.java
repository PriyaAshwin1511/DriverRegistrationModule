package com.demo.uber.registrationservice.converter;

import com.demo.uber.helperservice.converter.GenericConverter;
import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.registrationservice.dto.CabDriverProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class CabDriverProfileResponseConverter implements GenericConverter<CabDriverProfile, CabDriverProfileResponse> {
    @Override
    public CabDriverProfile createFromDto(CabDriverProfileResponse dto) {
        return null;
    }

    @Override
    public CabDriverProfileResponse createFromEntity(CabDriverProfile entity) {
        return CabDriverProfileResponse.builder()
                .driverId(entity.getId())
                .status(entity.getOnboardingStatus())
                .build();
    }

    @Override
    public CabDriverProfile updateEntity(CabDriverProfile entity, CabDriverProfileResponse dto) {
        return null;
    }
}
