package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @Email
        String email,

        @NotBlank
        String password
) {
}
