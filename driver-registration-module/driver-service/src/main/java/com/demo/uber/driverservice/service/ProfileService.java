package com.demo.uber.driverservice.service;

import com.demo.uber.driverservice.dto.CabDriverProfileDto;

public interface ProfileService {
    CabDriverProfileDto getCabDriverProfile(long driverID);

    boolean isValidDriver(String userName, String password);
}
