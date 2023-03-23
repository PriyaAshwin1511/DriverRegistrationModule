package com.demo.uber.driverservice.service;

import com.demo.uber.helperservice.dto.ResponseDto;
import com.demo.uber.helperservice.model.CabDriverAvailability;
import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.model.OnboardingStatus;
import com.demo.uber.helperservice.model.ResponseCode;
import com.demo.uber.helperservice.repository.CabDriverAvailabilityRepository;
import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Primary
public class AvailaibilityServiceImpl implements AvailaibilityService {
    private final CabDriverProfileRepository driverProfileRepository;
    private final CabDriverAvailabilityRepository availabilityRepository;
    @Override
    public ResponseDto markDriverAvailability(long driverId, long currentLocation) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CabDriverProfile> cabDriverProfile = driverProfileRepository.findById(driverId);

        if(!cabDriverProfile.isPresent()) {
           throw new RuntimeException("Driver not found");
        } else {
            CabDriverProfile profile = cabDriverProfile.get();
            if(profile.getOnboardingStatus() != OnboardingStatus.DEVICE_SHIPPED) {
                throw new RuntimeException("Driver has not completed onboarding.");
            }
            Optional<CabDriverAvailability> availabilityList = availabilityRepository.findByDriver(profile);
            if(!availabilityList.isPresent()) {
                CabDriverAvailability availability = CabDriverAvailability.builder()
                        .driver(profile)
                        .location(currentLocation)
                        .supportedRegion(profile.getRegion())
                        .isAvailable(true)
                        .updated(LocalDateTime.now())
                        .build();
                availabilityRepository.save(availability);
                responseDto.setMessage("Driver logged in");
            } else {
                CabDriverAvailability availability = availabilityList.get();
                if (!availability.isAvailable()) {
                    availability.setAvailable(true);
                    availability.setLocation(currentLocation);
                    availability.setUpdated(LocalDateTime.now());
                    availabilityRepository.save(availability);
                    responseDto.setMessage("Driver logged in");
                } else {
                    responseDto.setMessage("Driver Already logged In");
                }
            }
            responseDto.setResponseCode(ResponseCode.OK);
        }
        return responseDto;
    }

    @Override
    public ResponseDto markDriverUnAvailability(long driverId) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CabDriverProfile> cabDriverProfile = driverProfileRepository.findById(driverId);
        if(!cabDriverProfile.isPresent()) {
            throw new RuntimeException("Driver not found");
        } else {
            CabDriverAvailability availability = availabilityRepository.findByDriver(cabDriverProfile.get()).get();
            if(availability.isAvailable()) {
                availability.setAvailable(false);
                availability.setUpdated(LocalDateTime.now());
                availabilityRepository.save(availability);
                responseDto.setMessage("Driver logged out");
            } else {
                responseDto.setMessage("Driver is already logged out");
            }
            responseDto.setResponseCode(ResponseCode.OK);
        }
        return responseDto;
    }
}
