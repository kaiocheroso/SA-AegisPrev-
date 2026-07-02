package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.*;

public record MedicoRequestDTO (

        @NotBlank(message = "O nome do medico nao pode ser vazio")
        String nome,

        @NotBlank(message = "Nao pode ser vazio")
        String sexo,

        @NotNull(message = "nao pode ser nula")
        @Min(18)
        @Max(120)
        Integer idade,

        @NotBlank(message = "Email nao pode estar vazio")
        String email,
        @NotBlank(message = "Senha nao pode estar vazio")
        String password
){
}
