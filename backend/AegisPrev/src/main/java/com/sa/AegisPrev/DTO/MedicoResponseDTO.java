package com.sa.AegisPrev.DTO;

import com.sa.AegisPrev.models.Paciente;

import java.util.List;

public record MedicoResponseDTO(
        Long idMedico,
        String nome,
        String gamil,
        List<PacienteResponseDTO> pacientes
){
}
