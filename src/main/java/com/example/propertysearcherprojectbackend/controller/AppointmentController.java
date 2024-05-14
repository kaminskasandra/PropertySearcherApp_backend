package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.exceptions.AppointmentNotFoundException;
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
    public ResponseEntity<AppointmentDto> saveAppointment(@RequestBody AppointmentDto appointmentDto) {
        return ResponseEntity.ok(modelMapper.map(appointmentService.saveAppointment(appointmentDto), AppointmentDto.class));
    }

    @DeleteMapping(value = "{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
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
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(modelMapper.map(appointmentService.getAppointment(appointmentId), AppointmentDto.class));
    }

    @PutMapping(value = "{appointmentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> updateAppointment(@RequestBody AppointmentDto appointmentDto, @PathVariable Long appointmentId) throws AppointmentNotFoundException {
        return ResponseEntity.ok(modelMapper.map(appointmentService.updateAppointment(appointmentDto, appointmentId), AppointmentDto.class));
    }
}
