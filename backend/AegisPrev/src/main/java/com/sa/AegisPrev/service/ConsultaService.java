package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Consulta;
import com.sa.AegisPrev.models.Medico;
import com.sa.AegisPrev.models.Paciente;
import com.sa.AegisPrev.repository.MedicoRepository;
import com.sa.AegisPrev.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultaService {
    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;


    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public ConsultaResponseDTO salvar(ConsultaRequest dto){
        Medico medico = medicoRepository.findById(dto.idMedico()).orElseThrow(() -> new RecursoNaoEncontradoException("ID do medico nao encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.idPaciente()).orElseThrow(() -> new RecursoNaoEncontradoException("ID do paciente nao encontrado"));
        Consulta consulta = new Consulta();

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDataConsulta(LocalDateTime.now()); //talvez tenha que apagar o date do consultaRequest
        consulta.setDescricao(dto.descricao());

        Consulta salvo = repository.save(consulta);
        return toResponse(salvo);
    }

    private ConsultaResponseDTO toResponse(Consulta consulta){
        return new ConsultaResponseDTO(
                consulta.getIdConsulta(),
                new MedicoResumoDTO(
                        consulta.getMedico().getIdMedico(),
                        consulta.getMedico().getNome()
                ),
                new PacienteResumoDTO(
                        consulta.getPaciente().getIdPaciente(),
                        consulta.getPaciente().getNomePaciente(),
                        consulta.getPaciente().getCpfPaciente()
                ),
                consulta.getDataConsulta(),
                consulta.getDescricao(),
                consulta.getDoencas().stream().map(doenca -> new DoencaResumoDTO(
                        doenca.getIdDoenca(),
                        doenca.getNomeDoenca()
                )).toList()
        );
    }


}
