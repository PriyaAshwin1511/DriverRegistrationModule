package com.demo.uber.helperservice.model;

import com.demo.uber.helperservice.converter.EmptyStringToNullConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name="t_cab_driver_profile",
        uniqueConstraints = {@UniqueConstraint(name = "email_id_phone_number", columnNames = {"email_id"})},
        indexes = {
        @Index(name="contacts_index", columnList = "email_id, phone_number"),
        @Index(name="primary_key_index", columnList = "id")}
        )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabDriverProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", scale = 0, precision = 28)
    private long id;

    @Column(name = "first_name", length = 256)
    @Convert(converter = EmptyStringToNullConverter.class)
    private String firstName;

    @Column(name = "last_name", length = 256)
    @Convert(converter = EmptyStringToNullConverter.class)
    private String lastName;

    @Column(name = "email_id", length = 100)
    @Convert(converter = EmptyStringToNullConverter.class)
    private String emailId;

    @Column(name = "password", length = 256)
    private String password;

    @Column(name = "phone_number", length = 30)
    @Convert(converter = EmptyStringToNullConverter.class)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private SupportedRegion region;

    @Column(name = "country")
    @Enumerated(EnumType.STRING)
    private SupportedCountry country;

    @Column(name = "onboarding_status")
    @Enumerated(EnumType.STRING)
    private OnboardingStatus onboardingStatus;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "license_number")
    private String licenseNumber;
}
