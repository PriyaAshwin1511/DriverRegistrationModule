package com.demo.uber.registrationservice.service;

import com.demo.uber.helperservice.model.CabDriverDocument;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    CabDriverDocument uploadDocument(MultipartFile file, long driverId, String type);
}
