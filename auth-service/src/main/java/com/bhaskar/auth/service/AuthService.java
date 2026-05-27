package com.bhaskar.auth.service;

import com.bhaskar.auth.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);
}
