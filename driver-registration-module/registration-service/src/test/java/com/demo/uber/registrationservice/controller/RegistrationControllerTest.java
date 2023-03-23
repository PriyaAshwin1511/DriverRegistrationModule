package com.demo.uber.registrationservice.controller;

import com.demo.uber.helperservice.model.CabDriverProfile;
import com.demo.uber.helperservice.model.OnboardingStatus;
import com.demo.uber.helperservice.repository.CabDriverProfileDocumentRepository;
import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import com.demo.uber.registrationservice.converter.CabDriverDocumentResponseConverter;
import com.demo.uber.registrationservice.converter.CabDriverProfileResponseConverter;
import com.demo.uber.registrationservice.dto.CabDriverProfileRequest;
import com.demo.uber.registrationservice.dto.CabDriverProfileResponse;
import com.demo.uber.registrationservice.service.DocumentService;
import com.demo.uber.registrationservice.service.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(controllers = RegistrationController.class)
class RegistrationControllerTest {
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private DocumentService documentService;
    @MockBean
    private CabDriverProfileResponseConverter cabDriverProfileResponseConverter;
    @MockBean
    private CabDriverDocumentResponseConverter cabDriverDocumentResponseConverter;
    @MockBean
    private CabDriverProfileDocumentRepository documentRepository;
    @MockBean
    private CabDriverProfileRepository cabDriverProfileRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test should invoke upload document API")
    void shouldcreateProfileForDriver() throws Exception {
        CabDriverProfile profile = new CabDriverProfile();
        profile.setAvailable(false);
        profile.setId(2L);
        profile.setOnboardingStatus(OnboardingStatus.ACCOUNT_CREATED);
        Mockito.when(registrationService.registerDriver(Mockito.any(CabDriverProfileRequest.class))).thenReturn(profile);
        CabDriverProfileResponse response = CabDriverProfileResponse.builder()
                        .driverId(profile.getId())
                                .build();
        Mockito.when(cabDriverProfileResponseConverter.createFromEntity(Mockito.any(CabDriverProfile.class))).thenReturn(response);
        Map<String,Object> body = new HashMap<>();
        body.put("firstName","new");
        body.put("lastName","Employee");
        body.put("emailAddress","new@Employee.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/register/v1/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Created driver profile"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("CREATED"));
    }
}