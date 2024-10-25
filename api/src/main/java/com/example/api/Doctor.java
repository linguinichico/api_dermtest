package com.example.api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor {
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_SEQ") 
    @Column(name = "doctor_id") 
    private Long id;

    @Column(name = "name") 
    private String name;

    @Column(name = "email") 
    private String email;

    @Column(name = "numberPatients") 
    private Integer numberPatients;

    public Doctor() {}

    public Doctor(String name, String email, Integer numberPatients) {
        this.name = name;
        this.email = email;
        this.numberPatients = numberPatients;
    }
}
