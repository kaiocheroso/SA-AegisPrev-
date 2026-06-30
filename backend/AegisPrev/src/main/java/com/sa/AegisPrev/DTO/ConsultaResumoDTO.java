package com.sa.AegisPrev.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record ConsultaResumoDTO(
        Long idConsulta,
        Long idMedico,
        Long idPaciente,
        LocalDateTime dataConsulta,
        List<DoencaResumoDTO> doencas
) {
}
