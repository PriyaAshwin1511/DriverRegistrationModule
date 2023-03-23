package com.demo.uber.driverservice.service;

import com.demo.uber.helperservice.dto.ResponseDto;
import com.demo.uber.helperservice.model.CabDriverAvailability;
import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.model.OnboardingStatus;
import com.demo.uber.helperservice.model.ResponseCode;
import com.demo.uber.helperservice.repository.CabDriverAvailabilityRepository;
import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AvailaibilityServiceImplTest {

    @Mock
    private  CabDriverProfileRepository testDriverProfileRepository;
    @Mock
    private CabDriverAvailabilityRepository testAvailabilityRepository;

    @Test
    @DisplayName("Test should mark driver as available")
    void shouldMarkDriverAsAvailable() {
        AvailaibilityServiceImpl testAvailaibilityService = new AvailaibilityServiceImpl(testDriverProfileRepository, testAvailabilityRepository);
        CabDriverProfile driverProfile = CabDriverProfile.builder()
                .onboardingStatus(OnboardingStatus.DEVICE_SHIPPED)
                .id(123L).build();
        CabDriverAvailability availability = CabDriverAvailability.builder()
                .isAvailable(false)
                .build();
        ResponseDto expectedResponse = new ResponseDto();
        expectedResponse.setMessage("Driver logged in");
        expectedResponse.setResponseCode(ResponseCode.OK);
        Mockito.when(testDriverProfileRepository.findById(123L)).thenReturn(Optional.of(driverProfile));
        Mockito.when(testAvailabilityRepository.findByDriver(Mockito.any(CabDriverProfile.class))).thenReturn(Optional.of(availability));
        ResponseDto responseDto = testAvailaibilityService.markDriverAvailability(123L, 1234L);
        assert(availability.isAvailable());
        assertEquals(responseDto.getMessage(), expectedResponse.getMessage());
    }

    @Test
    @DisplayName("Test to display error message when driver is already logged in")
    public void shouldReturnErrorWhenDriverIsAlreadyLoggedIn(){
        AvailaibilityServiceImpl testAvailaibilityService = new AvailaibilityServiceImpl(testDriverProfileRepository,testAvailabilityRepository);
        CabDriverProfile driverProfile = CabDriverProfile.builder()
                .onboardingStatus(OnboardingStatus.DEVICE_SHIPPED)
                .id(123L).build();
        CabDriverAvailability availability = CabDriverAvailability.builder()
                .isAvailable(true)
                .build();
        ResponseDto expectedResponse = new ResponseDto();
        expectedResponse.setMessage("Driver Already logged In");
        expectedResponse.setResponseCode(ResponseCode.OK);
        Mockito.when(testDriverProfileRepository.findById(123L)).thenReturn(Optional.of(driverProfile));
        Mockito.when(testAvailabilityRepository.findByDriver(Mockito.any(CabDriverProfile.class))).thenReturn(Optional.of(availability));
        ResponseDto responseDto = testAvailaibilityService.markDriverAvailability(123L, 1234l);
        assert(availability.isAvailable());
        assertEquals(responseDto.getMessage(), expectedResponse.getMessage());
    }

    @Test()
    @DisplayName("Test to check for valid onboarding status before taking up a driver")
    public void shouldThrowExceptionOnInvalidStatus() {
        AvailaibilityServiceImpl testAvailaibilityService = new AvailaibilityServiceImpl(testDriverProfileRepository, testAvailabilityRepository);
        CabDriverProfile driverProfile = CabDriverProfile.builder()
                .onboardingStatus(OnboardingStatus.ACCOUNT_CREATED)
                .id(123L).build();
        Mockito.when(testDriverProfileRepository.findById(123L)).thenReturn(Optional.of(driverProfile));
        Throwable exception = Assert.assertThrows(RuntimeException.class,
                ()->{testAvailaibilityService.markDriverAvailability(123L, 24L);} );
        assertEquals(exception.getMessage(), "Driver has not completed onboarding.");
    }
}