package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.entity.Operation;
import com.example.hospitalmanagement.entity.OperationType;

import java.util.List;
import java.util.Optional;

public interface OperationService {
    Operation createOperation(Operation operation);
    Operation updateOperation(Long id, Operation operation);
    void deleteOperation(Long id);
    Operation findOperationById(Long id);
    List<Operation> findAllOperations();
    OperationType createOperationType(OperationType operationType);

    Optional<OperationType> getOperationTypeById(Long id);

    List<OperationType> getAllOperationTypes();

    OperationType updateOperationType(Long id, OperationType updatedOperationType);

    void deleteOperationType(Long id);
}