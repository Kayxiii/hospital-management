package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.repository.PatientRepository;
import com.example.hospitalmanagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient createPatient(Patient patient) {
        validatePatient(patient); // Validate input for creating a patient
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Patient ID must be greater than zero.");
        }

        validatePatient(patient); // Validate input for updating a patient

        return patientRepository.findById(id)
                .map(existingPatient -> {
                    existingPatient.setName(patient.getName());
                    existingPatient.setGender(patient.getGender());
                    existingPatient.setPatientType(patient.getPatientType());
                    existingPatient.setBirthDate(patient.getBirthDate());
                    existingPatient.setBloodGroup(patient.getBloodGroup());
                    existingPatient.setSymptoms(patient.getSymptoms());
                    existingPatient.setMobile(patient.getMobile());
                    existingPatient.setEmail(patient.getEmail());
                    existingPatient.setAddress(patient.getAddress());
                    return patientRepository.save(existingPatient);
                }).orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
    }

    @Override
    public void deletePatient(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Patient ID must be greater than zero.");
        }

        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with ID: " + id);
        }

        patientRepository.deleteById(id);
    }

    @Override
    public Patient findPatientById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Patient ID must be greater than zero.");
        }
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
    }

    @Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Validates a Patient object to ensure all required fields are present and valid.
     *
     * @param patient the Patient object to validate
     */
    private void validatePatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient object cannot be null.");
        }
        if (patient.getName() == null || patient.getName().isBlank()) {
            throw new IllegalArgumentException("Patient name is required.");
        }
        if (patient.getGender() == null || patient.getGender().isBlank()) {
            throw new IllegalArgumentException("Patient gender is required.");
        }
        if (patient.getPatientType() == null || patient.getPatientType().isBlank()) {
            throw new IllegalArgumentException("Patient type is required.");
        }
        if (patient.getBirthDate() == null || patient.getBirthDate().isBlank()) {
            throw new IllegalArgumentException("Patient birth date is required.");
        }
        if (patient.getBloodGroup() == null || patient.getBloodGroup().isBlank()) {
            throw new IllegalArgumentException("Patient blood group is required.");
        }
        if (patient.getMobile() == null || patient.getMobile().isBlank() || !patient.getMobile().matches("\\+\\d{10,15}")) {
            throw new IllegalArgumentException("Valid patient mobile number is required.");
        }
        if (patient.getEmail() == null || patient.getEmail().isBlank() || !patient.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Valid patient email is required.");
        }
        if (patient.getAddress() == null || patient.getAddress().isBlank()) {
            throw new IllegalArgumentException("Patient address is required.");
        }
    }
}
