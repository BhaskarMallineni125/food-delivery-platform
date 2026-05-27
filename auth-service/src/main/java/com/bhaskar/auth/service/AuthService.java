package com.bhaskar.auth.service;

import com.bhaskar.auth.dto.AuthResponse;
import com.bhaskar.auth.dto.LoginRequest;
import com.bhaskar.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
