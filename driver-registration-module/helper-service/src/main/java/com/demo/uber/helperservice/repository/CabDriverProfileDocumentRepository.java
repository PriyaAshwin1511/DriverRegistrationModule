package com.demo.uber.helperservice.repository;

import com.demo.uber.helperservice.model.CabDriverDocument;
import com.demo.uber.helperservice.model.CabDriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CabDriverProfileDocumentRepository extends JpaRepository<CabDriverDocument, Long> {
    List<CabDriverDocument> findByDriver(CabDriverProfile cabDriverProfile);
}
