package com.bhaskar.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/v1/user/profile")
    public String profile(Authentication authentication){
        return "Welcome " +authentication.getName();
    }

    @GetMapping("/api/v1/admin/dashboard")
    public String adminDashboard() {
        return "Welcome Admin";
    }
}
