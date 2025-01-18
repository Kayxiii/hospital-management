package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.entity.Operation;
import com.example.hospitalmanagement.entity.OperationType;
import com.example.hospitalmanagement.service.OperationService;
import com.example.hospitalmanagement.service.OperationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/operations/create")
    public Operation createOperation(@RequestBody Operation operation) {
        return operationService.createOperation(operation);
    }

    @GetMapping("/operations/get-all")
    public List<Operation> getAllOperations() {
        return operationService.findAllOperations();
    }

    @GetMapping("/operations/{id}")
    public ResponseEntity<Operation> getOperationById(@PathVariable Long id) {
        try {
            Operation operation = operationService.findOperationById(id);
            return ResponseEntity.ok(operation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/operations/update/{id}")
    public ResponseEntity<Operation> updateOperation(@PathVariable Long id, @RequestBody Operation updatedOperation) {
        try {
            Operation updated = operationService.updateOperation(id, updatedOperation);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/operations/delete/{id}")
    public ResponseEntity<Object> deleteOperation(@PathVariable Long id) {
        try {
            operationService.deleteOperation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
