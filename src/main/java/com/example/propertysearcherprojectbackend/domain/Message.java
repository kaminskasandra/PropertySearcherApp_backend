package com.example.propertysearcherprojectbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "MESSAGES")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long messageId;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "MESSAGE_CATEGORY", columnDefinition = "ENUM('SENT', 'RECEIVED', 'DELETED')")
    @Enumerated(EnumType.STRING)
    private MessageCategory messageCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FROM_USER_MAIL", referencedColumnName = "USER_MAIL")
    private User fromUserMail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TO_USER_MAIL", referencedColumnName = "USER_MAIL")
    private User toUserMail;

}
