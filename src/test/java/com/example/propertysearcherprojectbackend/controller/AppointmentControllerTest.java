package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.LocalDateAdapter;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.service.AppointmentService;
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
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveAppointmentTest() throws Exception {
        // Given
        Appointment appointment = new Appointment(1L, LocalDate.now(), null, null);
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentService.saveAppointment(any(AppointmentDto.class))).thenReturn(appointment);
        when(modelMapper.map(any(), eq(Appointment.class))).thenReturn(appointment);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(appointmentDto);

        // When & Then
        mockMvc.perform(post("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
        verify(appointmentService, times(1)).saveAppointment(any(AppointmentDto.class));
    }

    @Test
    void deleteAppointmentTest() throws Exception {
        // Given
        Long appointmentId = 1L;
        doNothing().when(appointmentService).deleteAppointmentById(appointmentId);

        //When & Then
        mockMvc.perform(delete("/v1/appointments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(appointmentService, times(1)).deleteAppointmentById(appointmentId);
        verifyNoMoreInteractions(appointmentService);
    }

    @Test
    void findAllAppointmentTest() throws Exception {
        //Given
        when(appointmentService.getAllAppointments()).thenReturn(Collections.emptyList());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(appointmentService, times(1)).getAllAppointments();
    }

    @Test
    public void testEmptyListOfAppointments() throws Exception {
        // Given
        when(appointmentService.getAllAppointments()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        verify(appointmentService, times(1)).getAllAppointments();
    }

    @Test
    void updateAppointmentTest() throws Exception {
        // Given
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentId(2L);
        Appointment appointment = new Appointment();

        when(appointmentService.updateAppointment(any(AppointmentDto.class), eq(2L))).thenReturn(appointment);
        when(modelMapper.map(any(), eq(AppointmentDto.class))).thenReturn(appointmentDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/appointments/{appointmentId}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(2));
    }
    @Test
    void findAppointmentById() throws Exception {

    }
}







