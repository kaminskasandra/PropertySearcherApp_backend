package com.example.propertysearcherprojectbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "REAL_ESTATE_AGENCIES")
public class RealEstateAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AGENCY_ID")
    private long agencyId;

    @Column(name = "AGENCY_NAME")
    private String agencyName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(targetEntity = Property.class,
            mappedBy = "realEstateAgency",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Property> properties;
}
