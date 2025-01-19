package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.entity.Admin;
public interface AdminService {
    Admin loginAsAdmin(String email, String password);
}