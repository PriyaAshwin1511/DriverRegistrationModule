package com.demo.uber.driverservice.controller;

import com.demo.uber.driverservice.dto.CabDriverProfileDto;
import com.demo.uber.driverservice.service.AvailaibilityService;
import com.demo.uber.driverservice.service.LocationService;
import com.demo.uber.driverservice.service.ProfileService;
import com.demo.uber.helperservice.dto.ResponseDto;
import com.demo.uber.helperservice.model.ResponseCode;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@Slf4j
public class DriverController {
    private final AvailaibilityService availaibilityService;
    private final LocationService locationService;

    private final ProfileService profileService;

    @PostMapping(value = "/v1/checkIn")
    @CircuitBreaker(name="checkIn", fallbackMethod = "fallBackForCheckIn")
    @TimeLimiter(name="checkIn")
    @Retry(name="checkIn")
    public CompletableFuture<ResponseDto> markDriverAvailability(@RequestParam("driverId") long driverId) {
        log.debug("Trying to mark a driver as available");
        long currentLocation = locationService.getCurrentLocation(driverId);
        return CompletableFuture.supplyAsync(() -> availaibilityService.markDriverAvailability(driverId, currentLocation));
    }

    public CompletableFuture<ResponseDto> fallBackForCheckIn(RuntimeException runtimeException) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponseCode(ResponseCode.ERROR);
        responseDto.setErrorMessage("Error checking in . Please try later");
        return CompletableFuture.supplyAsync(() -> responseDto);
    }

    @PostMapping(value = "/v1/checkOut")
    public ResponseDto markDriverUnAvailability(@RequestParam("driverId") long driverId ) {
        return availaibilityService.markDriverUnAvailability(driverId);
    }

    @GetMapping(value = "/v1/profile")
    public CabDriverProfileDto getDriverProfile(@RequestParam("driverId") long driverId ){
        return profileService.getCabDriverProfile(driverId);
    }

    @PostMapping(value = "/v1/login")
    public String login(@RequestParam("emailId") String emailId, @RequestParam("password") String password) {
        if(profileService.isValidDriver(emailId, password)){
            return "Successfully logged in. The driver can now edit profile or take ride";
        } else {
            return "The entered credentials do not match any existing account. Please register your account";
        }
    }
}
