package com.example.api;

class DoctorNotFoundException extends RuntimeException{
    DoctorNotFoundException(Long id){
        super("Could not find doctor " + id);
    }
}