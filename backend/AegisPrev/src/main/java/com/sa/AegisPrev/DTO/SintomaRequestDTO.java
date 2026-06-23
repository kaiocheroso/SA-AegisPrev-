package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SintomaRequestDTO (

        @NotBlank(message = " O sintoma deve conter um nome")
         String nomeSintoma,

         @NotBlank(message = "O sitoma deve conter uma descricao")
         String descricaoSintoma,

        List<Long> idsDoencas

){
}
