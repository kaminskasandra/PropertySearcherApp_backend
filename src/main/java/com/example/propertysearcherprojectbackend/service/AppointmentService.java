package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.exceptions.AppointmentNotFoundException;
import com.example.propertysearcherprojectbackend.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointment(final Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    public void deleteAppointmentById(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    public Appointment saveAppointment(AppointmentDto appointmentDto) {
        return appointmentRepository.save(modelMapper.map(appointmentDto, Appointment.class));
    }

    public Appointment updateAppointment(AppointmentDto appointmentDto, Long appointmentId) throws AppointmentNotFoundException {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new AppointmentNotFoundException(String.format("Appointment with id %s not found", appointmentDto.getAppointmentId()));
        } else {
            appointmentDto.setAppointmentId(appointmentId);
            return saveAppointment(appointmentDto);
        }
    }
}
