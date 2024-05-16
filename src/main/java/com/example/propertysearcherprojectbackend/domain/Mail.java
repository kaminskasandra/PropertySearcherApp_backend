package com.example.propertysearcherprojectbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
}