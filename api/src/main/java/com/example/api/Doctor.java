package com.example.api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
class Doctor {
    private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_SEQ") @Column(name = "doctor_id") Long id;
    private @Column(name = "name") String name;
    private @Column(name = "email") String email;
    private @Column(name = "numberPatients") Integer numberPatients;

    Doctor() {}

    Doctor(String name, String email, Integer numberPatients) {
        this.name = name;
        this.email = email;
        this.numberPatients = numberPatients;
    }

    // getters and setters

    public Long getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Integer getNumberPatients() {
        return this.numberPatients;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumberPatients(Integer nPatients) {
        this.numberPatients = nPatients;
    }
}
