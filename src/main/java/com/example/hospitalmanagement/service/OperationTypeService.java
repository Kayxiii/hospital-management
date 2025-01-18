package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.entity.OperationType;

import java.util.List;
import java.util.Optional;

public interface OperationTypeService {

    OperationType createOperationType(OperationType operationType);

    Optional<OperationType> getOperationTypeById(Long id);

    List<OperationType> getAllOperationTypes();

    OperationType updateOperationType(Long id, OperationType updatedOperationType);

    void deleteOperationType(Long id);
}
