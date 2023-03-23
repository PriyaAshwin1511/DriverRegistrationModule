package com.demo.uber.registrationservice.service;

import com.demo.uber.helperservice.model.CabDriverDocument;
import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.model.DocumentStatus;
import com.demo.uber.helperservice.model.DocumentType;
import com.demo.uber.helperservice.model.OnboardingStatus;
import com.demo.uber.helperservice.repository.CabDriverProfileDocumentRepository;
import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import com.demo.uber.registrationservice.config.Constants;
import com.demo.uber.registrationservice.event.DocumentCollectedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final int documentTypeCount = DocumentType.values().length;
    private final CabDriverProfileDocumentRepository documentRepository;
    private final CabDriverProfileRepository cabDriverProfileRepository;

    private final KafkaTemplate<String, DocumentCollectedEvent> kafkaTemplate;

    @Override
    public CabDriverDocument uploadDocument(MultipartFile file, long driverId, String type) {
        CabDriverDocument document = null;
        Optional<CabDriverProfile> cabDriverProfile = cabDriverProfileRepository.findById(driverId);
        if(cabDriverProfile.isPresent()) {
            document = CabDriverDocument.builder()
                    .documentType(DocumentType.valueOf(type))
                    .driver(cabDriverProfile.get())
                    .fileName(file.getOriginalFilename())
                    .documentStatus(DocumentStatus.UPLOADED)
                    .build();
           document =  documentRepository.save(document);
           updateProfileStatus(cabDriverProfile.get());
        } else {
            throw new RuntimeException("Invalid driver");
        }
        return document;
    }

    private void updateProfileStatus(CabDriverProfile cabDriverProfile) {
        if(documentRepository.findByDriver(cabDriverProfile).size() == documentTypeCount) {
            cabDriverProfile.setOnboardingStatus(OnboardingStatus.DOCUMENTS_COLLECTED);
            cabDriverProfileRepository.save(cabDriverProfile);
            sendMessage(cabDriverProfile.getId());
        }
    }

    private void sendMessage(long id) {

        kafkaTemplate.send(Constants.BACKGROUND_VERIFICATION_TOPIC, new DocumentCollectedEvent(id));
    }
}
