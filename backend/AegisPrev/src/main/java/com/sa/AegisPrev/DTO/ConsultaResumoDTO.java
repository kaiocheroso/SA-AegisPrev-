package com.sa.AegisPrev.DTO;

import java.time.LocalDateTime;

public record ConsultaResumoDTO(
        Long idConsulta,
        LocalDateTime dataConsulta,
        String descricao
) {
}
