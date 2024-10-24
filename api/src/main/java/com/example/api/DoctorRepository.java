package com.example.api;

import org.springframework.data.jpa.repository.JpaRepository;

interface DoctorRepository extends JpaRepository<Doctor, Long> {}