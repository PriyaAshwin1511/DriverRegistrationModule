package com.demo.uber.locationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationServiceController {
    @GetMapping(value = "/v1/current")
    public long getCabDriverLocation(){
        Random random = new Random();
        return random.longs(1000, 100000)
                .findFirst()
                .getAsLong();
    }
}
