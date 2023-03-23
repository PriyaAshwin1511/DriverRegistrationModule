package com.demo.uber.driverservice.service;

import com.demo.uber.helperservice.dto.ResponseDto;

public interface AvailaibilityService {
    ResponseDto markDriverAvailability(long driverId, long location);

    ResponseDto markDriverUnAvailability(long driverId);
}
