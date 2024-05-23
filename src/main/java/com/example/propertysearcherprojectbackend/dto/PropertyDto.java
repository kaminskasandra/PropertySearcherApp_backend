package com.example.propertysearcherprojectbackend.dto;

import com.example.propertysearcherprojectbackend.domain.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {
    private long propertyId;
    private PropertyType propertyType;
    private BigDecimal price;
    private String address;
    private String description;
    private double area;
    private Long userId;
}
