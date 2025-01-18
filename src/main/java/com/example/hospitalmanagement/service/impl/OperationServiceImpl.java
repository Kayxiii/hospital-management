package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.entity.Operation;
import com.example.hospitalmanagement.entity.OperationType;
import com.example.hospitalmanagement.repository.OperationRepository;
import com.example.hospitalmanagement.repository.OperationTypeRepository;
import com.example.hospitalmanagement.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Operation createOperation(Operation operation) {
        validateOperation(operation); // Validate input before saving
        return operationRepository.save(operation);
    }

    @Override
    public Operation updateOperation(Long id, Operation operation) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Operation ID must be greater than zero.");
        }

        validateOperation(operation);

        return operationRepository.findById(id)
                .map(existingOperation -> {
                    existingOperation.setPatient(operation.getPatient());
                    existingOperation.setOperationType(operation.getOperationType());
                    existingOperation.setDoctor(operation.getDoctor());
                    existingOperation.setDate(operation.getDate());
                    existingOperation.setTime(operation.getTime());
                    existingOperation.setDescription(operation.getDescription());
                    return operationRepository.save(existingOperation);
                }).orElseThrow(() -> new RuntimeException("Operation not found"));
    }

    @Override
    public void deleteOperation(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Operation ID must be greater than zero.");
        }

        if (!operationRepository.existsById(id)) {
            throw new RuntimeException("Operation not found with ID: " + id);
        }

        operationRepository.deleteById(id);
    }

    @Override
    public Operation findOperationById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Operation ID must be greater than zero.");
        }
        return operationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operation not found with ID: " + id));
    }

    @Override
    public List<Operation> findAllOperations() {
        return operationRepository.findAll();
    }

    /**
     * Validates an Operation object to ensure all required fields are present and valid.
     *
     * @param operation the Operation object to validate
     */
    private void validateOperation(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation object cannot be null.");
        }
        if (operation.getPatient() == null) {
            throw new IllegalArgumentException("Patient information is required.");
        }
        if (operation.getOperationType() == null) {
            throw new IllegalArgumentException("Operation Type information is required.");
        }
        if (operation.getDoctor() == null) {
            throw new IllegalArgumentException("Doctor information is required.");
        }
        if (operation.getDate() == null) {
            throw new IllegalArgumentException("Operation date is required.");
        }
        if (operation.getTime() == null) {
            throw new IllegalArgumentException("Operation time is required.");
        }
        if (operation.getDescription() == null || operation.getDescription().isBlank()) {
            throw new IllegalArgumentException("Operation description is required.");
        }
    }


}
