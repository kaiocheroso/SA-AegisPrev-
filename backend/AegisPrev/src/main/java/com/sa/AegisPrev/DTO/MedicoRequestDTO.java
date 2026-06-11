package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicoRequestDTO (

        @NotBlank(message = "O nome do medico nao pode ser vazio")
        String nome,

        @NotBlank(message = "Nao pode ser vazio")
        String sexo,

        @NotNull(message = "nao pode ser nula")
        Integer idade

){
}
