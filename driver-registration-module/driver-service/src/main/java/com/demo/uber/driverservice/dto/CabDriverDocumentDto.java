package com.demo.uber.driverservice.dto;

import com.demo.uber.helperservice.model.DocumentStatus;
import com.demo.uber.helperservice.model.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CabDriverDocumentDto {
    private String fileName;
    private DocumentType documentType;
    private DocumentStatus documentStatus;
    private LocalDateTime uploadDate;
}
