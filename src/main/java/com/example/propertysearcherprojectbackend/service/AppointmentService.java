package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.exceptions.AppointmentNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.AppointmentRepository;
import com.example.propertysearcherprojectbackend.repository.UserRepository;
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
    private final UserRepository userRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointment(final Long appointmentId) throws AppointmentNotFoundException {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new AppointmentNotFoundException("Appointment with id %s not found");
        } else {
            return appointmentRepository.findById(appointmentId);
        }
    }

    public void deleteAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new AppointmentNotFoundException("Appointment with id %s not found");
        } else {
            appointmentRepository.deleteById(appointmentId);
        }
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

    public List<Appointment> getAppointmentsByUserId(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getAppointments();
        } else throw new UserNotFoundException("User with given id not found");
    }
}

