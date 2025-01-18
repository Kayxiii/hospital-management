package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}