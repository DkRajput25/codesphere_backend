package com.dikshant.codesphere_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String email;

    // 👇 Table me column ka name password_hash hai, so yahi mapping karein:
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
