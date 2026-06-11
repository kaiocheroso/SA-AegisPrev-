package com.sa.AegisPrev.DTO;

import com.sa.AegisPrev.models.Consulta;
import com.sa.AegisPrev.models.Paciente;

import java.util.List;

public record MedicoResponseDTO(
        Long idMedico,
        String nome,
        String sexo,
        Integer idade,
        String email,
        List<ConsultaResumoDTO> consultas
){
}
