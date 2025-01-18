package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.entity.Doctor;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Doctor ID must be greater than zero.");
        }
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        validateDoctor(doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(int id, Doctor doctor) {
        if (id <= 0) {
            throw new IllegalArgumentException("Doctor ID must be greater than zero.");
        }
        validateDoctor(doctor);

        // Fetch the existing doctor
        Doctor existingDoctor = getDoctorById(id);

        // Update fields
        existingDoctor.setName(doctor.getName());
        existingDoctor.setDegree(doctor.getDegree());
        existingDoctor.setGender(doctor.getGender());
        existingDoctor.setBirthDate(doctor.getBirthDate());
        existingDoctor.setCharge(doctor.getCharge());
        existingDoctor.setMobile(doctor.getMobile());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setHAddress(doctor.getHAddress());
        existingDoctor.setOAddress(doctor.getOAddress());
        existingDoctor.setSpecialist(doctor.getSpecialist());
        existingDoctor.setImage(doctor.getImage());

        return doctorRepository.save(existingDoctor);
    }

    @Override
    public void deleteDoctor(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Doctor ID must be greater than zero.");
        }

        // Check if the doctor exists
        Doctor existingDoctor = getDoctorById(id);
        doctorRepository.delete(existingDoctor);
    }

    /**
     * Validates a Doctor object to ensure all required fields are present and valid.
     *
     * @param doctor the Doctor object to validate
     */
    private void validateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor object cannot be null.");
        }
        if (doctor.getName() == null || doctor.getName().isBlank()) {
            throw new IllegalArgumentException("Doctor name is required.");
        }
        if (doctor.getDegree() == null || doctor.getDegree().isBlank()) {
            throw new IllegalArgumentException("Doctor degree is required.");
        }
        if (doctor.getGender() == null || doctor.getGender().isBlank()) {
            throw new IllegalArgumentException("Doctor gender is required.");
        }
        if (doctor.getBirthDate() == null || doctor.getBirthDate().isBlank()) {
            throw new IllegalArgumentException("Doctor birth date is required.");
        }
        if (doctor.getCharge() <= 0) {
            throw new IllegalArgumentException("Doctor charge must be greater than zero.");
        }
        if (doctor.getMobile() == null || doctor.getMobile().isBlank() || !doctor.getMobile().matches("\\+\\d{10,15}")) {
            throw new IllegalArgumentException("Valid doctor mobile number is required.");
        }
        if (doctor.getEmail() == null || doctor.getEmail().isBlank() || !doctor.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Valid doctor email is required.");
        }
        if (doctor.getSpecialist() == null) {
            throw new IllegalArgumentException("Doctor specialization is required.");
        }
        if (doctor.getImage() == null) {
            throw new IllegalArgumentException("Doctor image is required.");
        }
    }
}
