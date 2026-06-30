package com.sa.AegisPrev.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ConsultaRequestDTO(
        @NotNull(message = "O id medico nao pode estar vazio")
        Long idMedico,
        @NotNull(message = "O id paciente nao pode estar vazio")
        Long idPaciente,
        @NotBlank(message = "Não pode estar vazio")
        String descricao,
        List<Long> sintomasIds
) {}
