package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.entity.Doctor;
import com.example.hospitalmanagement.entity.Operation;
import com.example.hospitalmanagement.entity.OperationType;
import com.example.hospitalmanagement.repository.DoctorRepository;
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
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private OperationTypeRepository operationTypeRepository;
    @Override
    public Operation createOperation(Operation operation) {
        if (operation.getDoctor() != null && operation.getDoctor().getId() != null) {
            operation.setDoctor(fetchDoctorById(Long.valueOf(operation.getDoctor().getId())));
        } else {
            throw new IllegalArgumentException("Valid Doctor ID must be provided.");
        }
        if (operation.getOperationType() != null && operation.getOperationType().getId() != null) {
            operation.setOperationType(fetchOperationTypeById(operation.getOperationType().getId()));
        } else {
            throw new IllegalArgumentException("Valid OperationType ID must be provided.");
        }
        validateOperation(operation);
        return operationRepository.save(operation);
    }

    @Override
    public Operation updateOperation(Long id, Operation operation) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Operation ID must be greater than zero.");
        }
        if (operation.getDoctor() != null && operation.getDoctor().getId() != null) {
            operation.setDoctor(fetchDoctorById(Long.valueOf(operation.getDoctor().getId())));
        } else {
            throw new IllegalArgumentException("Valid Doctor ID must be provided.");
        }
        if (operation.getOperationType() != null && operation.getOperationType().getId() != null) {
            operation.setOperationType(fetchOperationTypeById(operation.getOperationType().getId()));
        } else {
            throw new IllegalArgumentException("Valid OperationType ID must be provided.");
        }
        validateOperation(operation);
        return operationRepository.findById(id)
                .map(existingOperation -> {
                    existingOperation.setPatient(operation.getPatient());
                    existingOperation.setDoctor(operation.getDoctor());
                    existingOperation.setOperationType(operation.getOperationType());
                    existingOperation.setDate(operation.getDate());
                    existingOperation.setTime(operation.getTime());
                    existingOperation.setDescription(operation.getDescription());
                    return operationRepository.save(existingOperation);
                }).orElseThrow(() -> new RuntimeException("Operation not found with ID: " + id));
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
    private Doctor fetchDoctorById(Long doctorId) {
        return doctorRepository.findById(Math.toIntExact(doctorId))
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));
    }

    private OperationType fetchOperationTypeById(Long operationTypeId) {
        return operationTypeRepository.findById(operationTypeId)
                .orElseThrow(() -> new RuntimeException("OperationType not found with ID: " + operationTypeId));
    }



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
