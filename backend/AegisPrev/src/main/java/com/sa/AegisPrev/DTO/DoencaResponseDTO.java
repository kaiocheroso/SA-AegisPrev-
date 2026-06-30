package com.sa.AegisPrev.DTO;

import com.sa.AegisPrev.models.Consulta;
import com.sa.AegisPrev.models.Paciente;
import com.sa.AegisPrev.models.Sintoma;

import java.util.List;

public record DoencaResponseDTO (
        Long idDoenca,
        String nomeDoenca,
        String descricaoDoenca,
        Boolean hereditaria,
        List<SintomaResumoDTO> sintomas
){
}
