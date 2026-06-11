package com.sa.AegisPrev.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ConsultaRequest(
        @NotNull(message = "O id medico nao pode estar vazio")
        Long idMedico,
        @NotNull(message = "O id paciente nao pode estar vazio")
        Long idPaciente,
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDateTime dataConsulta,
        @NotBlank(message = "Não pode estar vazio")
        String descricao
) {}
