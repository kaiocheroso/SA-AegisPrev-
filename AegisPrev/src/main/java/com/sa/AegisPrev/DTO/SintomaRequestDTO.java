package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.NotBlank;

public record SintomaRequestDTO (

        @NotBlank(message = " O sintoma deve conter um nome")
         String nomeSintoma,

         @NotBlank(message = "O sitoma deve conter uma descricao")
         String descricaoSintoma

){
}
