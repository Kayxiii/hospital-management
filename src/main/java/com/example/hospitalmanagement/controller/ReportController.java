package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.entity.Report;
import com.example.hospitalmanagement.entity.ReportType;
import com.example.hospitalmanagement.service.ReportService;
import com.example.hospitalmanagement.service.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

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
