package com.example.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class DoctorControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DoctorRepository doctorRepository;

    // Set up mock data
    @BeforeEach
    void setUp() {
        doctorRepository.deleteAll();

        // Add a sample doctor to test with
        Doctor doctor1 = new Doctor("doctor_1", "doctor_1@example.com", 50);
        Doctor doctor2 = new Doctor("doctor_2", "doctor_2@example.com", 30);

        doctorRepository.saveAll(List.of(doctor1, doctor2));
    }

    @Test
    void testGetAllDoctors() {
        // Act - Perform GET request
        ResponseEntity<Doctor[]> response = restTemplate.getForEntity("/doctors", Doctor[].class);

        // Assert - Validate response
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void testGetDoctorById() {
        // Arrange
        Doctor savedDoctor = doctorRepository.findAll().get(0);

        // Act
        ResponseEntity<Doctor> response = restTemplate.getForEntity("/doctors/{id}", Doctor.class, savedDoctor.getId());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getId()).isEqualTo(savedDoctor.getId());
        assertThat(response.getBody().getName()).isEqualTo(savedDoctor.getName());
    }

    @Test
    void testPostNewDoctor() {
        // Arrange
        Doctor newDoctor = new Doctor("doctor_3", "doctor_3@example.com", 40);

        // Act - POST the new doctor
        HttpEntity<Doctor> request = new HttpEntity<>(newDoctor);
        ResponseEntity<Doctor> response = restTemplate.postForEntity("/doctors", request, Doctor.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getId()).isNotNull(); // New doctor should have an ID
        assertThat(response.getBody().getName()).isEqualTo(newDoctor.getName());
    }

    @Test
    void testUpdateDoctorById() {
        // Arrange
        Doctor existingDoctor = doctorRepository.findAll().get(0);
        Doctor updatedDoctor = new Doctor("updated_doctor", "updated_doctor@example.com", 70);

        // Act - Send PUT request
        HttpEntity<Doctor> request = new HttpEntity<>(updatedDoctor);
        ResponseEntity<Doctor> response = restTemplate.exchange("/doctors/{id}", HttpMethod.PUT, request, Doctor.class, existingDoctor.getId());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getName()).isEqualTo(updatedDoctor.getName());
        assertThat(response.getBody().getEmail()).isEqualTo(updatedDoctor.getEmail());
    }

    @Test
    void testDeleteDoctorById() {
        // Arrange
        Doctor doctorToDelete = doctorRepository.findAll().get(0);

        // Act - Send DELETE request
        ResponseEntity<Void> response = restTemplate.exchange("/doctors/{id}", HttpMethod.DELETE, null, Void.class, doctorToDelete.getId());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);

        // Confirm the doctor was deleted
        assertThat(doctorRepository.findById(doctorToDelete.getId())).isEmpty();
    }

    @Test
    void testDeleteAllDoctors() {
        // Act - Send DELETE request
        restTemplate.delete("/doctors");

        // Confirm the doctor was deleted
        assertThat(doctorRepository.findAll()).isEmpty();
    }

    @Test
    void testGetDoctorById_NotFound() {
        // Act - Try to get a non-existent doctor with an ID of 999L
        Long invalidId = 100L;
        ResponseEntity<String> response = restTemplate.getForEntity("/doctors/{id}", String.class, invalidId);
    
        // Assert - Ensure 404 is returned
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    
        // Assert - Ensure the response body contains the correct error message from DoctorNotFoundAdvice
        assertThat(response.getBody()).isEqualTo("Could not find doctor " + invalidId);
    }
}