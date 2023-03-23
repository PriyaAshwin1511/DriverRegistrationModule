package com.demo.uber.driverservice.dto;

import com.demo.uber.helperservice.model.OnboardingStatus;
import com.demo.uber.helperservice.model.SupportedCountry;
import com.demo.uber.helperservice.model.SupportedRegion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CabDriverProfileDto {
        private String firstName;
        private String lastName;
        private String emailId;
        private String phoneNumber;
        private String licenseNumber;
        private SupportedRegion region;
        private SupportedCountry country;
        private OnboardingStatus status;
        private List<CabDriverDocumentDto> cabDriverDocumentList;
}
