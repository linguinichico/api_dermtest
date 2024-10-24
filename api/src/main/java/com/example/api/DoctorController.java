package com.example.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
class DoctorController {

    private final DoctorRepository doctorRepository;

    DoctorController(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }
    
    // GET doctors

    @Operation(summary = "Get all doctors")
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        if (doctors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // POST doctor 
    @Operation(summary = "Post doctor")
    @PostMapping("/doctors")
    public ResponseEntity<Doctor> newDoctor(@RequestBody Doctor newDoctor) {
        return new ResponseEntity<>(doctorRepository.save(newDoctor),HttpStatus.OK);
    }

    // GET doctor by id
    @Operation(summary = "Get doctor by id")
    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor>getDoctorByID(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
        .orElseThrow(() -> new DoctorNotFoundException(id));

        return new ResponseEntity<>(doctor,HttpStatus.OK);
    }

    // PUT doctor new information by id
    @Operation(summary = "Put new information about a doctor by his id")
    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> replaceDoctorById(@RequestBody Doctor newDoctor, @PathVariable Long id) {
        return new ResponseEntity<>(doctorRepository.findById(id)
            .map(doctor -> {
                doctor.setName(newDoctor.getName());
                doctor.setEmail(newDoctor.getEmail());
                doctor.setNumberPatients(newDoctor.getNumberPatients());
                return doctorRepository.save(doctor);
            })
            .orElseGet(() -> {
                return doctorRepository.save(newDoctor);
            }), HttpStatus.OK);
    }

    // DELETE doctor by id
    @Operation(summary = "Delete a doctor by id")
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<HttpStatus> deleteDoctorById(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // DELETE all doctors
    @Operation(summary = "Delete all doctors")
    @DeleteMapping("/doctors")
    public ResponseEntity<HttpStatus> deleteAllDoctors() {
        doctorRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
