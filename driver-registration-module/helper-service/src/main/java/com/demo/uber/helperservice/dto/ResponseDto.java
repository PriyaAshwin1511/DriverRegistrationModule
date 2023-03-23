package com.demo.uber.helperservice.dto;

import com.demo.uber.helperservice.model.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private ResponseCode responseCode;
    private String errorMessage;
    private String message;
}
