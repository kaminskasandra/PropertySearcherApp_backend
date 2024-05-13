package com.example.propertysearcherprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateAgencyDto {
    private long agencyId;
    private String agencyName;
    private String phoneNumber;
}
