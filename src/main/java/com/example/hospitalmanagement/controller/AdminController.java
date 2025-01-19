package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.entity.Admin;
import com.example.hospitalmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login/admin")
    public Admin loginAsAdmin(@RequestParam String email, @RequestParam String password) {
        return adminService.loginAsAdmin(email, password);
    }
}