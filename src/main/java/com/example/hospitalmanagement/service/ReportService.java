package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.entity.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report createReport(Report report);
    Report updateReport(Long id, Report report);
    void deleteReport(Long id);
    Report findReportById(Long id);
    List<Report> findAllReports();

}