package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Consulta;
import com.sa.AegisPrev.models.Doenca;
import com.sa.AegisPrev.models.Sintoma;
import com.sa.AegisPrev.repository.DoencaRepository;
import com.sa.AegisPrev.repository.SintomaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SintomaService {
    private final SintomaRepository repository;
    private final DoencaRepository doencaRepository;

    public SintomaService(SintomaRepository repository, DoencaRepository doencaRepository) {
        this.repository = repository;
        this.doencaRepository = doencaRepository;
    }

    private SintomaResponseDTO toResponse(Sintoma sintoma){
        return new SintomaResponseDTO(
                sintoma.getIdSintoma(),
                sintoma.getNomeSintoma(),
                sintoma.getDescricaoSintoma(),
                sintoma.getPaciente().stream().map(paciente -> new PacienteResumoDTO(
                        paciente.getIdPaciente(),
                        paciente.getNomePaciente(),
                        paciente.getCpfPaciente()
                )).toList(),
                sintoma.getDoencas().stream().map(doenca -> new DoencaResumoDTO(
                        doenca.getIdDoenca(),
                        doenca.getNomeDoenca()
                )).toList()
        );
    }

    private Sintoma toEntity(SintomaRequestDTO dto){
        Sintoma sintoma = new Sintoma();

        sintoma.setNomeSintoma(dto.nomeSintoma());
        sintoma.setDescricaoSintoma(dto.descricaoSintoma());

        List<Doenca> doencas = doencaRepository.findAllById(dto.idsDoencas());
        sintoma.setDoencas(doencas);

        return sintoma;
    }

    public List<SintomaResponseDTO> listar(){
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SintomaResponseDTO buscarPorId(Long id){
        return toResponse(pegarId(id));
    }

    public SintomaResponseDTO cadastrar(SintomaRequestDTO dto){
        Sintoma sintoma = toEntity(dto);
        Sintoma salvo = repository.save(sintoma);
        return toResponse(salvo);
    }

    public SintomaResponseDTO atualizar(Long id, SintomaRequestDTO dto){
        Sintoma sintomaExistente = pegarId(id);

        sintomaExistente.setNomeSintoma(dto.nomeSintoma());
        sintomaExistente.setDescricaoSintoma(dto.descricaoSintoma());

        Sintoma salvo = repository.save(sintomaExistente);
        return toResponse(salvo);
    }

    public void deletar(Long id){
        Sintoma sintoma = pegarId(id);
        repository.delete(sintoma);
    }


    private Sintoma pegarId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("ID do sintoma nao encontrado"));
    }


}
