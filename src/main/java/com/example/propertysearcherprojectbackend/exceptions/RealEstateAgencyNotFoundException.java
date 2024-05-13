package com.example.propertysearcherprojectbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RealEstateAgencyNotFoundException extends Exception{
    public RealEstateAgencyNotFoundException(String message) {
        super(message);
    }
}