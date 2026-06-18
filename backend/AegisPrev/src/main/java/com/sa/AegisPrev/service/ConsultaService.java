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
import java.util.List;

@Service
public class ConsultaService {
    /// RAPAZIADA, TEREMOS QUE CRIAR O SINTOMA e fazer o resultado trazer com lista de possiveis doencas
    /// teremos que pegar os sintomas pelo paciente, e fazer com que as possiveis doencas aparecam

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;


    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
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

    public List<ConsultaResponseDTO> listar(){
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ConsultaResponseDTO buscarPorId(Long id){
        return toResponse(pegarId(id));
    }

    private Consulta pegarId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("ID não encontrado"));
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

    public ConsultaResponseDTO atualizar(ConsultaRequest dto){

    }


}
