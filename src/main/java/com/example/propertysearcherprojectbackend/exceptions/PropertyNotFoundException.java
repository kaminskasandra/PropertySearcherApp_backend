package com.example.propertysearcherprojectbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException(String message) {
        super(message);
    }
}
