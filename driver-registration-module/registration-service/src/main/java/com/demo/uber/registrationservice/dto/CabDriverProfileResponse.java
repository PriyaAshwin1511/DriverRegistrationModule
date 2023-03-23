package com.demo.uber.registrationservice.dto;

import com.demo.uber.helperservice.dto.ResponseDto;
import com.demo.uber.helperservice.model.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CabDriverProfileResponse extends ResponseDto {
    private long driverId;
    private OnboardingStatus status;
}
