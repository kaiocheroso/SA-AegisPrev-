package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.MedicoRequestDTO;
import com.sa.AegisPrev.DTO.MedicoResponseDTO;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Medico;
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
                medico.getEmail(),
                medico.getPassword(),
                medico.getPacientes()
        );
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

    public MedicoResponseDTO salvar(MedicoRequestDTO request){
        Medico medico = new Medico();
        medico.setEmail(request.email());
        medico.setPassword(request.password());

        Medico salvo = medicoRepository.save(medico);
        return toResponseDTO(salvo);
    }
}
