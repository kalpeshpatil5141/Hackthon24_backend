package com.hackathon24backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_otp")
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOtp {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp",nullable = false)
    private String otp;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // Foreign key reference
    private User user;
}
