package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(int id);
    Doctor saveDoctor(Doctor doctor);
    Doctor updateDoctor(int id, Doctor doctor);
    void deleteDoctor(int id);
}
