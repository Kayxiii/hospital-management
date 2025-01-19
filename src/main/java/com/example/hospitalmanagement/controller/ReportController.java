package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.entity.Report;
import com.example.hospitalmanagement.entity.ReportType;
import com.example.hospitalmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // ReportType Endpoints

    @GetMapping("/reportTypes/{id}")
    public ResponseEntity<ReportType> getReportTypeById(@PathVariable Long id) {
        return reportService.getReportTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reportTypes/get-all")
    public List<ReportType> getAllReportTypes() {
        return reportService.getAllReportTypes();
    }

    @PostMapping("/reportTypes/create")
    public ReportType createReportType(@RequestBody ReportType reportType) {
        return reportService.createReportType(reportType);
    }

    @PutMapping("/reportTypes/update/{id}")
    public ResponseEntity<?> updateReportType(@PathVariable Long id, @RequestBody ReportType updatedReportType) {
        try {
            return ResponseEntity.ok(reportService.updateReportType(id, updatedReportType));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reportTypes/delete/{id}")
    public ResponseEntity<?> deleteReportType(@PathVariable Long id) {
        try {
            reportService.deleteReportType(id);
            return ResponseEntity.ok("Report type deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Report Endpoints

    @GetMapping("/reports/get-all")
    public List<Report> getAllReports() {
        return reportService.findAllReports();
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reportService.findReportById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reports/create")
    public ResponseEntity<?> createReport(@RequestBody Report report) {
        try {
            Report savedReport = reportService.createReport(report);
            return ResponseEntity.ok(savedReport);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reports/update/{id}")
    public ResponseEntity<?> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
        try {
            return ResponseEntity.ok(reportService.updateReport(id, updatedReport));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reports/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.ok("Report deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
