package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.PacienteRequestDTO;
import com.sa.AegisPrev.DTO.PacienteResponseDTO;
import com.sa.AegisPrev.models.Paciente;
import com.sa.AegisPrev.repository.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    private PacienteResponseDTO toResponseDTO(Paciente paciente){
        return new PacienteResponseDTO(
                paciente.getIdPaciente(),
                paciente.getNomePaciente(),
                paciente.getCpfPaciente()
        );
    }

    private Paciente toEntity(PacienteRequestDTO dto){

    }
}
