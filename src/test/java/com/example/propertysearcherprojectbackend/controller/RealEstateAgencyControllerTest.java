package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.LocalDateAdapter;
import com.example.propertysearcherprojectbackend.domain.RealEstateAgency;
import com.example.propertysearcherprojectbackend.dto.RealEstateAgencyDto;
import com.example.propertysearcherprojectbackend.service.RealEstateAgencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RealEstateAgencyController.class)
@ExtendWith(MockitoExtension.class)
public class RealEstateAgencyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RealEstateAgencyService realEstateAgencyService;
    @MockBean
    private ModelMapper modelMapper;
    @InjectMocks
    private RealEstateAgencyController realEstateAgencyController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveAgencyTest() throws Exception {
        //Given
        RealEstateAgency realEstateAgency = new RealEstateAgency();
        when(realEstateAgencyService.saveAgency(any(RealEstateAgencyDto.class))).thenReturn(realEstateAgency);
        when(modelMapper.map(any(), eq(RealEstateAgency.class))).thenReturn(realEstateAgency);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(realEstateAgency);

        //When & Then
        mockMvc.perform(post("/v1/agencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
        verify(realEstateAgencyService, times(1)).saveAgency(any(RealEstateAgencyDto.class));
    }
    @Test
    void deleteAgencyTest() throws Exception {
        // Given
        Long agencyId = 1L;
        doNothing().when(realEstateAgencyService).deleteAgencyById(agencyId);

        //When & Then
        mockMvc.perform(delete("/v1/agencies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(realEstateAgencyService, times(1)).deleteAgencyById(agencyId);
        verifyNoMoreInteractions(realEstateAgencyService);
    }
    @Test
    void findAllAgenciesTest() throws Exception {
        //Given
        when(realEstateAgencyService.getAllAgencies()).thenReturn(Collections.emptyList());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/agencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(realEstateAgencyService, times(1)).getAllAgencies();
    }
    @Test
    void updateAgencyTest() throws Exception {
        // Given
        RealEstateAgencyDto realEstateAgencyDto = new RealEstateAgencyDto();
        realEstateAgencyDto.setAgencyId(2L);
        RealEstateAgency realEstateAgency = new RealEstateAgency();

        when(realEstateAgencyService.updateAgency(any(RealEstateAgencyDto.class), eq(2L))).thenReturn(realEstateAgency);
        when(modelMapper.map(any(), eq(RealEstateAgencyDto.class))).thenReturn(realEstateAgencyDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/agencies/{agencyId}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(realEstateAgencyDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.agencyId").value(2));
    }
}



