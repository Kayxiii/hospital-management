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
public class ReportTypeController {

    @Autowired
    private ReportTypeService reportTypeService;

    // ReportType Endpoints

    @GetMapping("/reportTypes/{id}")
    public ResponseEntity<ReportType> getReportTypeById(@PathVariable Long id) {
        return reportTypeService.getReportTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reportTypes/get-all")
    public List<ReportType> getAllReportTypes() {
        return reportTypeService.getAllReportTypes();
    }

    @PostMapping("/reportTypes/create")
    public ReportType createReportType(@RequestBody ReportType reportType) {
        return reportTypeService.createReportType(reportType);
    }

    @PutMapping("/reportTypes/update/{id}")
    public ResponseEntity<?> updateReportType(@PathVariable Long id, @RequestBody ReportType updatedReportType) {
        try {
            return ResponseEntity.ok(reportTypeService.updateReportType(id, updatedReportType));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reportTypes/delete/{id}")
    public ResponseEntity<?> deleteReportType(@PathVariable Long id) {
        try {
            reportTypeService.deleteReportType(id);
            return ResponseEntity.ok("Report type deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
