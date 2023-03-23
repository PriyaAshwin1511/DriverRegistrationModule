package com.demo.uber.driverservice.service;

import com.demo.uber.driverservice.dto.CabDriverDocumentDto;
import com.demo.uber.driverservice.dto.CabDriverProfileDto;
import com.demo.uber.helperservice.model.CabDriverDocument;
import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.repository.CabDriverProfileDocumentRepository;
import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final CabDriverProfileRepository cabDriverProfileRepository;
    private final CabDriverProfileDocumentRepository cabDriverProfileDocumentRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public CabDriverProfileDto getCabDriverProfile(long driverId) {
        Optional<CabDriverProfile> cabDriverProfiles = cabDriverProfileRepository.findById(driverId);
        if (!cabDriverProfiles.isPresent()){
            throw new RuntimeException("Invalid cab driver id");
        }
        List<CabDriverDocument> cabDriverDocumentList = cabDriverProfileDocumentRepository.findByDriver(cabDriverProfiles.get());
        CabDriverProfile cabDriverProfile = cabDriverProfiles.get();
        CabDriverProfileDto cabDriverProfileDto = CabDriverProfileDto.builder()
                .emailId(cabDriverProfile.getEmailId())
                .status(cabDriverProfile.getOnboardingStatus())
                .firstName(cabDriverProfile.getFirstName())
                .lastName(cabDriverProfile.getLastName())
                .phoneNumber(cabDriverProfile.getPhoneNumber())
                .licenseNumber(cabDriverProfile.getLicenseNumber())
                .region(cabDriverProfile.getRegion())
                .country(cabDriverProfile.getCountry())
                .cabDriverDocumentList(
                        cabDriverDocumentList.stream()
                                .map(this::generateDocumentDto)
                                .collect(Collectors.toList()))
                .build();
        return cabDriverProfileDto;
    }

    @Override
    public boolean isValidDriver(String emailId, String password) {
        return cabDriverProfileRepository.findByEmailId(emailId)
                .stream()
                .anyMatch((profile) -> passwordEncoder.matches(password, profile.getPassword()));
    }

    private CabDriverDocumentDto generateDocumentDto(CabDriverDocument cabDriverDocument) {
        return  CabDriverDocumentDto.builder()
                .documentStatus(cabDriverDocument.getDocumentStatus())
                .fileName(cabDriverDocument.getFileName())
                .documentType(cabDriverDocument.getDocumentType())
                .uploadDate(cabDriverDocument.getCreated())
                .build();
    }
}
