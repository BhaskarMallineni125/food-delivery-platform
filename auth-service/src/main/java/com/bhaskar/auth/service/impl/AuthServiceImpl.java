package com.bhaskar.auth.service.impl;

import com.bhaskar.auth.dto.RegisterRequest;
import com.bhaskar.auth.entity.User;
import com.bhaskar.auth.repository.UserRepository;
import com.bhaskar.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public String register(RegisterRequest request){

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        userRepository.save(user);

        return "User registered successfully";
    }
}
