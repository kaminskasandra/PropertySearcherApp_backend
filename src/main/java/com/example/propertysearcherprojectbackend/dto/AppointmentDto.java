package com.example.propertysearcherprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    private long appointmentId;
    private LocalDate dateOfMeeting;
    private long propertyId;
    private long userId;
}
