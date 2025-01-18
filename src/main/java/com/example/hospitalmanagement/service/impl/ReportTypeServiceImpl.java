package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.entity.ReportType;
import com.example.hospitalmanagement.repository.ReportTypeRepository;
import com.example.hospitalmanagement.service.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ReportTypeServiceImpl implements ReportTypeService {

    @Autowired
    private ReportTypeRepository reportTypeRepository;

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
