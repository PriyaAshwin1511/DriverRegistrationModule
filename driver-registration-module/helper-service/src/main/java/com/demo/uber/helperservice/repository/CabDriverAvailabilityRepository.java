package com.demo.uber.helperservice.repository;

import com.demo.uber.helperservice.model.CabDriverAvailability;
import com.demo.uber.helperservice.model.CabDriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CabDriverAvailabilityRepository extends JpaRepository<CabDriverAvailability, Long> {
    Optional<CabDriverAvailability> findByDriver(CabDriverProfile cabDriverProfile);
}
