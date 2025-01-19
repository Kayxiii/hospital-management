package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmailAndIsAdmin(String email, boolean isAdmin);
}