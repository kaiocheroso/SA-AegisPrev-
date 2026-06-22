package com.sa.AegisPrev.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record ConsultaResponseDTO(
        Long idConsulta,
        MedicoResumoDTO medico,
        PacienteResumoDTO paciente,
        LocalDateTime dataConsulta,
        String descricao,
        List<DoencaResumoDTO> doencas,
        List<DoencaPrevistaDTO> previsoes
) {
}
