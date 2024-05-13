package com.example.propertysearcherprojectbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class AppointmentNotFoundException extends Exception{
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}