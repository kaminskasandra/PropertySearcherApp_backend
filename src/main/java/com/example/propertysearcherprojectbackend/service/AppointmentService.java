package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.exceptions.AppointmentNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.AppointmentRepository;
import com.example.propertysearcherprojectbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;
    private final AuditMessageService auditMessageService;
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
            auditMessageService.saveAuditMessage(AuditMessage.builder()
                    .message("Appointment deleted: appointment id " + appointmentId)
                    .createdAt(LocalDateTime.now())
                    .build());
            appointmentRepository.deleteById(appointmentId);
        }
    }

    public Appointment saveAppointment(AppointmentDto appointmentDto) {
       Appointment appointment = appointmentRepository.save(modelMapper.map(appointmentDto, Appointment.class));
        auditMessageService.saveAuditMessage(AuditMessage.builder()
                .message("New appointment added: " + appointment.getAppointmentId() + " " + appointment.getDateOfMeeting() + " " +appointment.getDescription())
                .createdAt(LocalDateTime.now())
                .build());
        return appointment;
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

