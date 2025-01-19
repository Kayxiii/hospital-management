package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.entity.Operation;
import com.example.hospitalmanagement.entity.OperationType;
import com.example.hospitalmanagement.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OperationController {

    @Autowired
    private OperationService operationService;

    // OperationType Endpoints

    @GetMapping("/operationTypes/get-all")
    public List<OperationType> getAllOperationTypes() {
        return operationService.getAllOperationTypes();
    }

    @GetMapping("/operationTypes/{id}")
    public ResponseEntity<OperationType> getOperationTypeById(@PathVariable Long id) {
        return operationService.getOperationTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/operationTypes/create")
    public OperationType createOperationType(@RequestBody OperationType operationType) {
        return operationService.createOperationType(operationType);
    }

    @PutMapping("/operationTypes/update/{id}")
    public ResponseEntity<OperationType> updateOperationType(@PathVariable Long id,
                                                             @RequestBody OperationType updatedOperationType) {
        try {
            OperationType updated = operationService.updateOperationType(id, updatedOperationType);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/operationTypes/delete/{id}")
    public ResponseEntity<Object> deleteOperationType(@PathVariable Long id) {
        try {
            operationService.deleteOperationType(id);
            return ResponseEntity.ok("Operation type deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Operation Endpoints

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
            return ResponseEntity.ok("Operation deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
