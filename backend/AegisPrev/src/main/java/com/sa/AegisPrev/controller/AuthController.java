package com.sa.AegisPrev.controller;

import com.sa.AegisPrev.DTO.AuthRequestDTO;
import com.sa.AegisPrev.DTO.AuthResponseDTO;
import com.sa.AegisPrev.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO dto){
        return authService.login(dto);
    }
}
