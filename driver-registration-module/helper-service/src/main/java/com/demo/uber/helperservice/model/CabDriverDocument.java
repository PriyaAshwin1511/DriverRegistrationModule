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
@Table(name="t_cab_driver_document",
        uniqueConstraints = {@UniqueConstraint(name = "driver_type", columnNames = {"driver_id", "type"})},
        indexes = {@Index(name="driver_index", columnList = "driver_id")}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabDriverDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", scale = 0, precision = 28)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private CabDriverProfile driver;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;

    @Column(name = "created_on")
    private LocalDateTime created;

    @Column(name = "expires_on")
    private LocalDateTime expiryDate;

    @Column(name = "file_name", length = 100)
    @Convert(converter = EmptyStringToNullConverter.class)
    private String fileName;
}
