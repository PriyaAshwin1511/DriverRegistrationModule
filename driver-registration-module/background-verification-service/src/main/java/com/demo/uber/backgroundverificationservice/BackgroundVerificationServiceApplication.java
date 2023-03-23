package com.demo.uber.backgroundverificationservice;

import com.demo.uber.backgroundverificationservice.config.Constants;
import com.demo.uber.backgroundverificationservice.event.DocumentCollectedEvent;
import com.demo.uber.backgroundverificationservice.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories("com.demo.*")
@EntityScan("com.demo.*")
public class BackgroundVerificationServiceApplication {
    @Autowired
    private VerificationService verificationService;

    public static void main(String[] args) {
        SpringApplication.run(BackgroundVerificationServiceApplication.class, args);
    }

    @KafkaListener(topics= Constants.BACKGROUND_VERIFICATION_TOPIC)
    public void handleMessage(DocumentCollectedEvent event){
        verificationService.verifyCabDriverProfile(event.getId());
    }

}
