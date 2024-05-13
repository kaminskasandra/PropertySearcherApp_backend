package com.example.propertysearcherprojectbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "USER_LAST_NAME")
    private String userLastName;

    @Column(name = "USER_MAIL")
    private String mail;
}
