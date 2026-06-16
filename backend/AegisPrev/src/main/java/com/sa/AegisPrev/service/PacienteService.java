package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.*;
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
                paciente.getCpfPaciente(),
                paciente.getDataNascimento(),
                paciente.getSintomas().stream().map(sintoma -> new SintomaResumoDTO(
                        sintoma.getIdSintoma(),
                        sintoma.getNomeSintoma()
                )).toList(),
                paciente.getDoencas().stream().map(doenca -> new DoencaResumoDTO(
                        doenca.getIdDoenca(),
                        doenca.getNomeDoenca()
                )).toList(),
                paciente.getConsultas().stream().map(consulta -> new ConsultaResumoDTO(
                        consulta.getIdConsulta(),
                        consulta.getMedico().getIdMedico(),
                        consulta.getPaciente().getIdPaciente(),
                        consulta.getDataConsulta(),
                        consulta.getDescricao()
                )).toList()
        );
    }

    private Paciente toEntity(PacienteRequestDTO dto){
            Paciente paciente = new Paciente();
            paciente.setNomePaciente(dto.nomePaciente());
            paciente.setCpfPaciente(dto.cpfPaciente());
            paciente.setDataNascimento(dto.dataNascimento());

    }

}
