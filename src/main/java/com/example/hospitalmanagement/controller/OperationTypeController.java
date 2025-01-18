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
public class OperationTypeController {

    @Autowired
    private OperationTypeService operationTypeService;

    // OperationType Endpoints

    @PostMapping("/operationTypes/create")
    public OperationType createOperationType(@RequestBody OperationType operationType) {
        return operationTypeService.createOperationType(operationType);
    }

    @GetMapping("/operationTypes/get-all")
    public List<OperationType> getAllOperationTypes() {
        return operationTypeService.getAllOperationTypes();
    }

    @GetMapping("/operationTypes/{id}")
    public ResponseEntity<OperationType> getOperationTypeById(@PathVariable Long id) {
        return operationTypeService.getOperationTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/operationTypes/update/{id}")
    public ResponseEntity<OperationType> updateOperationType(@PathVariable Long id,
                                                             @RequestBody OperationType updatedOperationType) {
        try {
            OperationType updated = operationTypeService.updateOperationType(id, updatedOperationType);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/operationTypes/delete/{id}")
    public ResponseEntity<Object> deleteOperationType(@PathVariable Long id) {
        try {
            operationTypeService.deleteOperationType(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
