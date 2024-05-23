package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.exceptions.AppointmentNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.PropertyNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.AppointmentRepository;
import com.example.propertysearcherprojectbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    private final UserService userService;
    private final PropertyService propertyService;

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

    @Transactional
    public Appointment saveAppointment(AppointmentDto appointmentDto) throws UserNotFoundException, PropertyNotFoundException {
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        User user = userService.getUser(appointmentDto.getUserId());
        appointment.setUser(user);
        Property property = propertyService.getProperty(appointmentDto.getPropertyId());
        appointment.setProperty(property);

        appointment = appointmentRepository.save(appointment);
        auditMessageService.saveAuditMessage(AuditMessage.builder()
                .message("New appointment added: " + appointment.getAppointmentId() + " " + appointment.getDateOfMeeting() + " " + appointment.getDescription())
                .createdAt(LocalDateTime.now())
                .build());
        return appointment;
    }

    public Appointment updateAppointment(AppointmentDto appointmentDto, Long appointmentId) throws AppointmentNotFoundException, UserNotFoundException, PropertyNotFoundException {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new AppointmentNotFoundException(String.format("Appointment with id %s not found", appointmentDto.getAppointmentId()));
        } else {
            appointmentDto.setAppointmentId(appointmentId);
            return saveAppointment(appointmentDto);
        }
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) throws UserNotFoundException {
        User user = userService.getUser(userId);

        return appointmentRepository.findAllByUser(user);
    }
}

