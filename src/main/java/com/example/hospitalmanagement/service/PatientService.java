package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.entity.Patient;

import java.util.List;

public interface PatientService {
    Patient createPatient(Patient patient);
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);
    Patient findPatientById(Long id);
    List<Patient> findAllPatients();
}