package com.demo.uber.registrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabDriverProfileRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private String region;
    private String country;
    private String password;
    private String licenseNumber;
}
