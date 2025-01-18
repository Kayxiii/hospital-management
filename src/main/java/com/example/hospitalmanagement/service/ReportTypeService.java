package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.entity.ReportType;

import java.util.List;
import java.util.Optional;

public interface ReportTypeService {

    ReportType createReportType(ReportType reportType);
    List<ReportType> getAllReportTypes();
    Optional<ReportType> getReportTypeById(Long id);
    ReportType updateReportType(Long id, ReportType updatedReportType);
    void deleteReportType(Long id);
}
