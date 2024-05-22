package com.example.propertysearcherprojectbackend.dto;

import com.example.propertysearcherprojectbackend.domain.MessageCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String text;
    private LocalDate date;
    private MessageCategory messageCategory;
    private String fromUserMail;
    private String toUserMail;
}
