package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MedicoRequestDTO (

        @NotBlank(message = "O nome do medico nao pode ser vazio")
        String nome,

        @Email
        @NotBlank(message = "O medico deve conter um nome")
        String email,

        @NotBlank(message = " O medico deve conter uma senha")
        String password

){
}
