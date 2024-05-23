package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.exceptions.AppointmentNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.PropertyNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;

import com.example.propertysearcherprojectbackend.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PropertyService propertyService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AuditMessageService auditMessageService;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAppointments() {
        //Given
        List<Appointment> appointments = List.of(new Appointment(), new Appointment());

        //When & Then
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAllAppointments();

        assertEquals(2, result.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetAppointment() throws AppointmentNotFoundException {
        // Given
        Appointment appointment = new Appointment(1L, LocalDate.of(2000, 12, 30), "test description", null, null);

        // When & Then
        when(appointmentRepository.existsById(1L)).thenReturn(true);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Optional<Appointment> result = appointmentService.getAppointment(1L);

        assertTrue(result.isPresent());
        assertEquals(appointment, result.get());
    }


    @Test
    public void testDeleteAppointmentById() throws AppointmentNotFoundException {

        when(appointmentRepository.existsById(1L)).thenReturn(true);

        appointmentService.deleteAppointmentById(1L);

        verify(appointmentRepository, times(1)).deleteById(1L);
        verify(auditMessageService, times(1)).saveAuditMessage(any());
    }


    @Test
    public void testSaveAppointment() throws UserNotFoundException, PropertyNotFoundException {
        // Given
        AppointmentDto appointmentDto = new AppointmentDto(1L, LocalDate.of(2000, 12, 30), "test description", 1L, 1L);
        User user = new User();
        user.setUserId(1L);
        Property property = new Property();
        property.setPropertyId(1L);
        Appointment appointment = new Appointment(1L, LocalDate.of(2000, 12, 30), "test description", property, user);

        // When
        when(userService.getUser(1L)).thenReturn(user);
        when(propertyService.getProperty(1L)).thenReturn(property);
        when(modelMapper.map(appointmentDto, Appointment.class)).thenReturn(appointment);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Then
        Appointment result = appointmentService.saveAppointment(appointmentDto);
        assertNotNull(result);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(auditMessageService, times(1)).saveAuditMessage(any());
    }

    @Test
    public void testGetAppointmentsByUserId() throws UserNotFoundException {
        //Given
        User user = new User();
        List<Appointment> appointments = List.of(new Appointment(), new Appointment());

        //When & Then
        when(userService.getUser(1L)).thenReturn(user);
        when(appointmentRepository.findAllByUser(user)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByUserId(1L);

        assertEquals(2, result.size());
        verify(appointmentRepository, times(1)).findAllByUser(user);
    }
}

