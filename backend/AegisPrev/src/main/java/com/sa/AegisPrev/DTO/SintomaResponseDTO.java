package com.sa.AegisPrev.DTO;

import com.sa.AegisPrev.models.Paciente;

import java.util.List;

public record SintomaResponseDTO (
        Long idSintoma,
        String nomeSintoma,
        String descricaoSintoma,
        List<PacienteResumoDTO> pacientes,
        List<DoencaResumoDTO> doencas
){
}
