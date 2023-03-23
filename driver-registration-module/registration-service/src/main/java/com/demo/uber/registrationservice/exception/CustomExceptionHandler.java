package com.demo.uber.registrationservice.exception;

import com.demo.uber.helperservice.dto.ResponseDto;
import com.demo.uber.helperservice.model.ResponseCode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {
    @ExceptionHandler(value= {Exception.class})
    public ResponseEntity handleException(Exception exception) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setErrorMessage(exception.getMessage());
        responseDto.setResponseCode(ResponseCode.ERROR);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
