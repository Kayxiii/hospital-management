package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.entity.Admin;
import com.example.hospitalmanagement.repository.AdminRepository;
import com.example.hospitalmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin loginAsAdmin(String email, String password) {
        Optional<Admin> adminOptional = adminRepository.findByEmailAndIsAdmin(email, true);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (passwordEncoder.matches(password, admin.getPassword())) {
                return admin;
            }
            throw new IllegalArgumentException("Invalid password");
        }
        throw new IllegalArgumentException("Admin admin not found");
    }
}