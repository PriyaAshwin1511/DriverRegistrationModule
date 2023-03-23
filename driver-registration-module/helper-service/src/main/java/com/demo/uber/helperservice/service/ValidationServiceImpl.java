package com.demo.uber.helperservice.service;

import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    private final CabDriverProfileRepository driverProfileRepository;

    @Override
    public boolean isDriverValid(long id) {
        return driverProfileRepository.findById(id).isPresent();
    }
}
