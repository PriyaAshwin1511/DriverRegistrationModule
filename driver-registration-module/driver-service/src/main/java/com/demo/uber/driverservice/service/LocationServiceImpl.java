package com.demo.uber.driverservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final WebClient.Builder webClientBuilder;
    @Override
    public long getCurrentLocation(long id) {
        return webClientBuilder.build().get()
                .uri("http://location-service/location/v1/current")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }
}
