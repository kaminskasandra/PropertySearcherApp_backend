package com.example.propertysearcherprojectbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "PROPERTIES")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROPERTY_ID")
    private long propertyId;

    @Column(name = "PROPERTY_TYPE", columnDefinition = "ENUM('APARTMENT', 'SINGLE_FAMILY_HOUSE', 'BUILDING_PLOT', 'PREMISES_FOR_RENT')")
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "ADRRESS")
    private String address;

    @Column(name = "AREA")
    private double area;

    @ManyToOne
    @JoinColumn(name = "AGENCY_ID")
    private RealEstateAgency realEstateAgency;
}
