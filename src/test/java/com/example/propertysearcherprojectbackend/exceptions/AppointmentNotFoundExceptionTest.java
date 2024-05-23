package com.example.propertysearcherprojectbackend.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AppointmentNotFoundExceptionTest {

    @Test
    public void shouldThrowAppointmentNotFoundException() {
        //Given
        String expectedMessage = "Appointment with id %s not found";

        //When & Then
        assertThatThrownBy(() -> {
            throw new AppointmentNotFoundException(expectedMessage);
        }).isInstanceOf(AppointmentNotFoundException.class)
                .hasMessage(expectedMessage);
    }
}
