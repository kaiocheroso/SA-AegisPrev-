package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MedicoRequestDTO (

        @Email
        @NotBlank(message = "O medico deve conter um nome")
        String gamil,

        @NotBlank(message = " O medico deve conter uma senha")
        String password,

        Long idUsuario

){
}
