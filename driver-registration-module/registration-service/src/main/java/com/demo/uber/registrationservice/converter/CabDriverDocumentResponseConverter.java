package com.demo.uber.registrationservice.converter;

import com.demo.uber.helperservice.converter.GenericConverter;
import com.demo.uber.helperservice.model.CabDriverDocument;
import com.demo.uber.registrationservice.dto.CabDriverDocumentResponse;
import org.springframework.stereotype.Component;

@Component
public class CabDriverDocumentResponseConverter implements GenericConverter<CabDriverDocument, CabDriverDocumentResponse> {
    @Override
    public CabDriverDocument createFromDto(CabDriverDocumentResponse dto) {
        return null;
    }

    @Override
    public CabDriverDocumentResponse createFromEntity(CabDriverDocument entity) {
        return CabDriverDocumentResponse.builder()
                .driverId(entity.getDriver().getId())
                .documentStatus(entity.getDocumentStatus())
                .documentType(entity.getDocumentType())
                .build();
    }

    @Override
    public CabDriverDocument updateEntity(CabDriverDocument entity, CabDriverDocumentResponse dto) {
        return null;
    }
}
