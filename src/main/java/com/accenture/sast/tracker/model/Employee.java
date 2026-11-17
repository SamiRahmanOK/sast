package com.accenture.sast.tracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;
    private String email;
    private LocalDate onboardingDate;

    public Employee() {}

    public Employee(String name, String role, String email, LocalDate onboardingDate) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.onboardingDate = onboardingDate;
    }

    public Long getId()                       { return id; }
    public String getName()                   { return name; }
    public void setName(String name)          { this.name = name; }
    public String getRole()                   { return role; }
    public void setRole(String role)          { this.role = role; }
    public String getEmail()                  { return email; }
    public void setEmail(String email)        { this.email = email; }
    public LocalDate getOnboardingDate()      { return onboardingDate; }
    public void setOnboardingDate(LocalDate d){ this.onboardingDate = d; }
}
