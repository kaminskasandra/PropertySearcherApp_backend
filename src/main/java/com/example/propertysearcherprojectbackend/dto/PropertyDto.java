package com.example.propertysearcherprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {
    private long propertyId;
    private String address;
    private BigDecimal price;
    private double area;
    private long realEstateAgencyId;
}
