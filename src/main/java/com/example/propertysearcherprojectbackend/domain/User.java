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
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "USER_LAST_NAME")
    private String userLastName;

    @Column(name = "USER_MAIL")
    private String mail;

    @OneToMany(targetEntity = Appointment.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Appointment> appointments;
}
