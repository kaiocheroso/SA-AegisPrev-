package com.sa.AegisPrev.DTO;

import java.time.LocalDateTime;

public record ConsultaResumoDTO(
        Long idConsulta,
        Long idMedico,
        Long idPaciente,
        LocalDateTime dataConsulta,
        String descricao
) {
}
