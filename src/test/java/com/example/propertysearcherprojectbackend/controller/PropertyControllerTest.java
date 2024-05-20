package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.LocalDateAdapter;
import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.dto.PropertyDto;
import com.example.propertysearcherprojectbackend.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropertyController.class)
@ExtendWith(MockitoExtension.class)
public class PropertyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PropertyService propertyService;
    @MockBean
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveProperty() throws Exception{
        //Given
        Property property = new Property();
        when(propertyService.saveProperty(any(PropertyDto.class))).thenReturn(property);
        when(modelMapper.map(any(), eq(Property.class))).thenReturn(property);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, LocalDateAdapter.getInstance())
                .create();
        String jsonContent = gson.toJson(property);

        //When & Then
        mockMvc.perform(post("/v1/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
        verify(propertyService, times(1)).saveProperty(any(PropertyDto.class));
    }
    @Test
    void deletePropertyTest() throws Exception {
        // Given
        Long propertyId = 1L;
        doNothing().when(propertyService).deletePropertyById(propertyId);

        //When & Then
        mockMvc.perform(delete("/v1/property/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(propertyService, times(1)).deletePropertyById(propertyId);
        verifyNoMoreInteractions(propertyService);
    }
    @Test
    void findAllPropertiesTest() throws Exception {
        //Given
        when(propertyService.getAllProperties()).thenReturn(Collections.emptyList());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/property")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(propertyService, times(1)).getAllProperties();
    }
    @Test
    void updatePropertyTest() throws Exception {
        // Given
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setPropertyId(2L);
        Property property = new Property();

        when(propertyService.updateProperty(any(PropertyDto.class), eq(2L))).thenReturn(property);
        when(modelMapper.map(any(), eq(PropertyDto.class))).thenReturn(propertyDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/property/{propertyId}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.propertyId").value(2));
    }
    @Test
    void getPropertyByIdTest() throws Exception {
        // Given
    }
}

