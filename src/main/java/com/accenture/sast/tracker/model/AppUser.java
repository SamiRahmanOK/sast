package com.accenture.sast.tracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String passwordHash;

    public AppUser() {}

    public AppUser(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public Long getId()                           { return id; }
    public String getUsername()                   { return username; }
    public void setUsername(String username)      { this.username = username; }
    public String getPasswordHash()               { return passwordHash; }
    public void setPasswordHash(String h)         { this.passwordHash = h; }
}
