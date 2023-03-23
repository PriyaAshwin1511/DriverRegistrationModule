package com.demo.uber.registrationservice.service;

import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.registrationservice.dto.CabDriverProfileRequest;

public interface RegistrationService {
    CabDriverProfile registerDriver(CabDriverProfileRequest request);
}
