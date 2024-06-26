package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.exceptions.AppointmentNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.PropertyNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> saveAppointment(@RequestBody AppointmentDto appointmentDto) throws UserNotFoundException, PropertyNotFoundException {
        return ResponseEntity.ok(modelMapper.map(appointmentService.saveAppointment(appointmentDto), AppointmentDto.class));
    }

    @DeleteMapping(value = "{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) throws AppointmentNotFoundException {
        appointmentService.deleteAppointmentById(appointmentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<AppointmentDto> findAll() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long appointmentId) throws AppointmentNotFoundException {
        return ResponseEntity.ok(modelMapper.map(appointmentService.getAppointment(appointmentId), AppointmentDto.class));
    }

    @GetMapping("user/{userId}")
    public List<AppointmentDto> getAppointmentsByUser(@PathVariable Long userId) throws UserNotFoundException {
        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(userId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping(value = "{appointmentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> updateAppointment(@RequestBody AppointmentDto appointmentDto, @PathVariable Long appointmentId) throws AppointmentNotFoundException, UserNotFoundException, PropertyNotFoundException {
        return ResponseEntity.ok(modelMapper.map(appointmentService.updateAppointment(appointmentDto, appointmentId), AppointmentDto.class));
    }
}
