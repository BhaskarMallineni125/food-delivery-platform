package com.bhaskar.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
