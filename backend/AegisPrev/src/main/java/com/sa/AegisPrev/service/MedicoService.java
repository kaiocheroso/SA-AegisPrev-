package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.MedicoRequestDTO;
import com.sa.AegisPrev.DTO.MedicoResponseDTO;
import com.sa.AegisPrev.DTO.PacienteResponseDTO;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Medico;
import com.sa.AegisPrev.models.Paciente;
import com.sa.AegisPrev.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    private MedicoResponseDTO toResponseDTO(Medico medico) {

        return new MedicoResponseDTO(
                medico.getIdMedico(),
                medico.getNome(),
                medico.getEmail(),
                medico.getPacientes()
                        .stream().
                        map(paciente -> new PacienteResponseDTO(
                                paciente.getIdPaciente(),
                                paciente.getNomePaciente(),
                                paciente.getCpfPaciente()
                        ))
                        .toList()
        );
    }

    private Medico toEntity(MedicoRequestDTO dto){
        Medico medico = new Medico();
        medico.setNome(dto.nome());
        medico.setSexo(dto.sexo());
        medico.setIdade(dto.idade());
        medico.setEmail(dto.email());
        medico.setPassword(dto.password());
        return medico;
    }

    public List<MedicoResponseDTO> listar() {
        return medicoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public MedicoResponseDTO buscarPorId(Long idMedico){
        Medico medico = medicoRepository.findById(idMedico).orElseThrow(() -> new RecursoNaoEncontradoException("ID nao encontrado"));
        return toResponseDTO(medico);
    }

    public MedicoResponseDTO buscarPorEmail(String email){
         Medico medico = medicoRepository.findByEmail(email).orElseThrow(() -> new RecursoNaoEncontradoException("Email nao encontrado"));
         return toResponseDTO(medico);
    }

    public List<MedicoResponseDTO> buscarPorNome(String nome){
        return medicoRepository.findByNomeEqualsIgnoreCase(nome)
                .stream()
                .map(
                        this::toResponseDTO
                )
                .toList();
    }

    public MedicoResponseDTO salvar(MedicoRequestDTO request){
        Medico medico = toEntity(request);
        Medico salvo = medicoRepository.save(medico);
        return toResponseDTO(salvo);
    }

    public MedicoResponseDTO atualizar(Long idMedico, MedicoRequestDTO request){
        Medico medicoExistente = medicoRepository.findById(idMedico).orElseThrow(() -> new RecursoNaoEncontradoException("ID do medico nao encontrado"));
        medicoExistente.setNome(request.nome());
        medicoExistente.setSexo(request.sexo());
        medicoExistente.setIdade(request.idade());
        medicoExistente.setEmail(request.email());
        medicoExistente.setPassword(request.password());
        Medico atualizado = medicoRepository.save(medicoExistente);
        return toResponseDTO(atualizado);
    }

    public void deletar(Long idMedico){
        Medico medico = medicoRepository.findById(idMedico).orElseThrow(() -> new RecursoNaoEncontradoException("ID nao encontrado"));
        medicoRepository.delete(medico);
    }
}
