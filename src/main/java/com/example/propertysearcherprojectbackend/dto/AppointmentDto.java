package com.example.propertysearcherprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    private Long appointmentId;
    private LocalDate dateOfMeeting;
    private String description;
    private Long propertyId;
    private Long userId;
}
