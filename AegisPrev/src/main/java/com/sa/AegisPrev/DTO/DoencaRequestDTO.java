package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record DoencaRequestDTO (

        @NotBlank(message = "O campo nome esta em branco")
        String nome,

        @NotBlank(message = "O campo nome esta em branco")

        @CPF(message = "O CPF informado é inválido")
        String cpf

) {
}
