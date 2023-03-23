package com.demo.uber.helperservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name="t_cab_driver_availability",
        uniqueConstraints = {@UniqueConstraint(name = "driver_type", columnNames = {"driver_id", "updated_on"})},
        indexes = {@Index(name="location_index", columnList = "location, region")}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabDriverAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", scale = 0, precision = 28)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private CabDriverProfile driver;

    @Column(name = "is_online")
    private boolean isAvailable;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private SupportedRegion supportedRegion;

    @Column(name = "location", precision = 20)
    private long location;

    @Column(name = "updated_on")
    private LocalDateTime updated;
}
