package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record PacienteRequestDTO (

        @NotBlank(message = "O paciente deve conter um nome ")
        String nome,
        @CPF(message = "O CPF informado é inválido")
        String cpf

){
}
