package com.demo.uber.registrationservice.controller;

import com.demo.uber.helperservice.model.CabDriverDocument;
import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.model.ResponseCode;
import com.demo.uber.registrationservice.converter.CabDriverDocumentResponseConverter;
import com.demo.uber.registrationservice.converter.CabDriverProfileResponseConverter;
import com.demo.uber.registrationservice.dto.CabDriverDocumentResponse;
import com.demo.uber.registrationservice.dto.CabDriverProfileRequest;
import com.demo.uber.registrationservice.dto.CabDriverProfileResponse;
import com.demo.uber.registrationservice.service.DocumentService;
import com.demo.uber.registrationservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final DocumentService documentService;
    private final CabDriverProfileResponseConverter cabDriverProfileResponseConverter;
    private final CabDriverDocumentResponseConverter cabDriverDocumentResponseConverter;

    @PostMapping(value = "/v1/profile")
    public CabDriverProfileResponse createDriverProfile(@RequestBody CabDriverProfileRequest cabDriverProfileRequest) {
        CabDriverProfile profile = registrationService.registerDriver(cabDriverProfileRequest);
        CabDriverProfileResponse response = cabDriverProfileResponseConverter.createFromEntity(profile);
        response.setResponseCode(ResponseCode.CREATED);
        response.setMessage("Created driver profile");
        return response;
    }

    @PostMapping(value = "/v1/documents")
    public CabDriverDocumentResponse uploadOnboardingDocuments(@RequestParam("files") MultipartFile file,
                                                               @RequestParam("driverId") long driverId,
                                                               @RequestParam("documentType")String type
                                                              )   {
        CabDriverDocument document = documentService.uploadDocument(file, driverId, type);
        return cabDriverDocumentResponseConverter.createFromEntity(document);
    }
}
