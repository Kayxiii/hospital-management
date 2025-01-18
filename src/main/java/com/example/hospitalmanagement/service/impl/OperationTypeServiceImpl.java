package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.entity.OperationType;
import com.example.hospitalmanagement.repository.OperationTypeRepository;
import com.example.hospitalmanagement.service.OperationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OperationTypeServiceImpl implements OperationTypeService {
    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Override
    public OperationType createOperationType(OperationType operationType) {
        validateOperationType(operationType);
        return operationTypeRepository.save(operationType);
    }

    @Override
    public Optional<OperationType> getOperationTypeById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("OperationType ID must be greater than zero.");
        }
        return operationTypeRepository.findById(id);
    }

    @Override
    public List<OperationType> getAllOperationTypes() {
        return operationTypeRepository.findAll();
    }

    @Override
    public OperationType updateOperationType(Long id, OperationType updatedOperationType) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("OperationType ID must be greater than zero.");
        }
        validateOperationType(updatedOperationType);

        return operationTypeRepository.findById(id)
                .map(existingOperationType -> {
                    existingOperationType.setName(updatedOperationType.getName());
                    existingOperationType.setCost(updatedOperationType.getCost());
                    return operationTypeRepository.save(existingOperationType);
                })
                .orElseThrow(() -> new RuntimeException("OperationType not found with ID: " + id));
    }

    @Override
    public void deleteOperationType(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("OperationType ID must be greater than zero.");
        }

        if (!operationTypeRepository.existsById(id)) {
            throw new RuntimeException("OperationType not found with ID: " + id);
        }

        operationTypeRepository.deleteById(id);
    }

    private void validateOperationType(OperationType operationType) {
        if (operationType == null) {
            throw new IllegalArgumentException("OperationType object cannot be null.");
        }
        if (operationType.getName() == null || operationType.getName().isBlank()) {
            throw new IllegalArgumentException("OperationType name is required.");
        }
        if (operationType.getCost() == null || operationType.getCost() <= 0) {
            throw new IllegalArgumentException("OperationType cost must be greater than zero.");
        }
    }
}
