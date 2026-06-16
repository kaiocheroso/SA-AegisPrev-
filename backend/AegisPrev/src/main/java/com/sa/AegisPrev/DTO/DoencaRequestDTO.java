package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record DoencaRequestDTO (

        Long idDoenca,
        String nomeDoenca,
        String descricaoDoenca,
        Boolean hereditaria
) {
}
