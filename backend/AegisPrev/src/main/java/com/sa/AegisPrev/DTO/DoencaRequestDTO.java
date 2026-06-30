package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record DoencaRequestDTO (
        @NotBlank(message = "O nome nao pode estar vazio")
        String nomeDoenca,
        @NotBlank(message = "A descrição nao pode estar vazio")
        String descricaoDoenca,
        @NotNull(message = "Deve ser true ou false")
        Boolean hereditaria,
        List<Long> idSintomas
) {
}
