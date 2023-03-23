package com.demo.uber.driverservice.controller;

import com.demo.uber.driverservice.service.AvailaibilityService;
import com.demo.uber.driverservice.service.LocationService;
import com.demo.uber.helperservice.dto.ResponseDto;
import com.demo.uber.helperservice.model.ResponseCode;
import com.demo.uber.helperservice.repository.CabDriverProfileDocumentRepository;
import com.demo.uber.helperservice.repository.CabDriverProfileRepository;
import org.hamcrest.Matchers;
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

@WebMvcTest(controllers = DriverController.class)
public class DriverControllerTest {

    @MockBean
    private AvailaibilityService mockAvilabilityService;
    @MockBean
    private CabDriverProfileRepository driverProfileRepository;
    @MockBean
    private LocationService locationService;
    @MockBean
    private CabDriverProfileDocumentRepository cabDriverProfileDocumentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test should invoke driver checkout API")
    public void shouldMarkDriverAsUnavailable() throws Exception {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponseCode(ResponseCode.OK);
        responseDto.setMessage("Driver logged out");
        Mockito.when(mockAvilabilityService.markDriverUnAvailability(1L)).thenReturn(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/driver/v1/checkOut")
                .param("driverId", String.valueOf(1L)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Driver logged out"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("OK"));
    }
}