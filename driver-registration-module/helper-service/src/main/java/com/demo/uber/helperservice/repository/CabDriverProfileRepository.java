package com.demo.uber.helperservice.repository;

import com.demo.uber.helperservice.model.CabDriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CabDriverProfileRepository extends JpaRepository<CabDriverProfile, Long> {
    Optional<CabDriverProfile> findById(long id);
    List<CabDriverProfile> findByEmailId(String emailId);
}
