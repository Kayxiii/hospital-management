package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.entity.Report;
import com.example.hospitalmanagement.service.PatientService;
import com.example.hospitalmanagement.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ReportRepository reportRepository;

    @PostMapping("/create")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.findAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(patientService.findPatientById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        try {
            return ResponseEntity.ok(patientService.updatePatient(id, updatedPatient));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok("Patient deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPatients(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String patientType
    ) {
        if (id != null) {
            try {
                return ResponseEntity.ok(patientService.findPatientById(id));
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        } else if (name != null && !name.isBlank()) {
            List<Patient> patients = patientService.findAllPatients().stream()
                    .filter(patient -> patient.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
            if (patients.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(patients);
        } else {
            return ResponseEntity.badRequest().body(
                    "At least one search parameter (id, name, or patientType) must be provided.");
        }
    }

    // Retrieve all reports for a specific patient
    @GetMapping("/{id}/reports")
    public ResponseEntity<List<Report>> getReportsForPatient(@PathVariable Long id) {
        try {
            Patient patient = patientService.findPatientById(id);
            List<Report> reports = reportRepository.findByPatientId(id);
            return ResponseEntity.ok(reports);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
