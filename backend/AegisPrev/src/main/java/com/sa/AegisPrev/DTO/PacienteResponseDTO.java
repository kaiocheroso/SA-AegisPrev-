package com.sa.AegisPrev.DTO;

import java.time.LocalDate;
import java.util.List;

public record PacienteResponseDTO (
        Long idPaciente,
        String nomePaciente,
        String cpfPaciente,
        LocalDate dataNascimento,
        List<ConsultaResumoDTO> consultas

){
}
