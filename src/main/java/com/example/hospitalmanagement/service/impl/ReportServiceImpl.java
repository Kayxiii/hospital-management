package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.entity.Report;
import com.example.hospitalmanagement.entity.ReportType;
import com.example.hospitalmanagement.repository.ReportRepository;
import com.example.hospitalmanagement.repository.ReportTypeRepository;
import com.example.hospitalmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;


    @Override
    public Report createReport(Report report) {
        validateReport(report); // Validate input before saving
        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(Long id, Report report) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Report ID must be greater than zero.");
        }

        validateReport(report); // Validate input for updating a report

        return reportRepository.findById(id)
                .map(existingReport -> {
                    existingReport.setPatient(report.getPatient());
                    existingReport.setReportType(report.getReportType());
                    existingReport.setDescription(report.getDescription());
                    existingReport.setImage(report.getImage());
                    existingReport.setSize(report.getSize());
                    existingReport.setType(report.getType());
                    return reportRepository.save(existingReport);
                }).orElseThrow(() -> new RuntimeException("Report not found with ID: " + id));
    }

    @Override
    public void deleteReport(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Report ID must be greater than zero.");
        }

        if (!reportRepository.existsById(id)) {
            throw new RuntimeException("Report not found with ID: " + id);
        }

        reportRepository.deleteById(id);
    }

    @Override
    public Report findReportById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Report ID must be greater than zero.");
        }
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with ID: " + id));
    }

    @Override
    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }

    /**
     * Validates a Report object to ensure all required fields are present and valid.
     *
     * @param report the Report object to validate
     */
    private void validateReport(Report report) {
        if (report == null) {
            throw new IllegalArgumentException("Report object cannot be null.");
        }
        if (report.getPatient() == null) {
            throw new IllegalArgumentException("Patient information is required.");
        }
        if (report.getReportType() == null) {
            throw new IllegalArgumentException("Report Type information is required.");
        }
        if (report.getDescription() == null || report.getDescription().isBlank()) {
            throw new IllegalArgumentException("Report description is required.");
        }
        if (report.getImage() == null || report.getImage().isBlank()) {
            throw new IllegalArgumentException("Report image is required.");
        }
        if (report.getSize() == null || report.getSize() <= 0) {
            throw new IllegalArgumentException("Report size must be greater than zero.");
        }
        if (report.getType() == null || report.getType().isBlank()) {
            throw new IllegalArgumentException("Report type is required.");
        }
    }

    @Override
    public ReportType createReportType(ReportType reportType) {
        validateReportType(reportType);
        return reportTypeRepository.save(reportType);
    }

    @Override
    public List<ReportType> getAllReportTypes() {
        return reportTypeRepository.findAll();
    }

    @Override
    public Optional<ReportType> getReportTypeById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ReportType ID must be greater than zero.");
        }
        return reportTypeRepository.findById(id);
    }

    @Override
    public ReportType updateReportType(Long id, ReportType updatedReportType) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ReportType ID must be greater than zero.");
        }
        validateReportType(updatedReportType);

        return reportTypeRepository.findById(id)
                .map(existingReportType -> {
                    existingReportType.setName(updatedReportType.getName());
                    existingReportType.setCost(updatedReportType.getCost());
                    return reportTypeRepository.save(existingReportType);
                })
                .orElseThrow(() -> new RuntimeException("ReportType not found with ID: " + id));
    }

    @Override
    public void deleteReportType(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ReportType ID must be greater than zero.");
        }

        if (!reportTypeRepository.existsById(id)) {
            throw new RuntimeException("ReportType not found with ID: " + id);
        }

        reportTypeRepository.deleteById(id);
    }

    private void validateReportType(ReportType reportType) {
        if (reportType == null) {
            throw new IllegalArgumentException("ReportType object cannot be null.");
        }
        if (reportType.getName() == null || reportType.getName().isBlank()) {
            throw new IllegalArgumentException("ReportType name is required.");
        }
        if (reportType.getCost() == null || reportType.getCost() <= 0) {
            throw new IllegalArgumentException("ReportType cost must be greater than zero.");
        }
    }
}
