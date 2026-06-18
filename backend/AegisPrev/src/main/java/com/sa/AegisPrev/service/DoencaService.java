package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.DoencaResponseDTO;
import com.sa.AegisPrev.DTO.SintomaResumoDTO;
import com.sa.AegisPrev.models.Doenca;
import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.*;
import com.sa.AegisPrev.repository.DoencaRepository;
import org.springframework.stereotype.Service;

@Service
public class DoencaService {
    private final DoencaRepository doencaRepository;

    public DoencaService(DoencaRepository doencaRepository) {
        this.doencaRepository = doencaRepository;
    }

    public DoencaResponseDTO toResponseDTO(Doenca doenca){
        return new DoencaResponseDTO(
                doenca.getIdDoenca(),
                doenca.getNomeDoenca(),
                doenca.getDescricaoDoenca(),
                doenca.getHereditaria(),

                doenca.getSintomas().stream().map(sintoma -> new SintomaResumoDTO(
                        sintoma.getIdSintoma(),
                        sintoma.getNomeSintoma()
                )).toList()
        );
    }

    public void deletar(Long IdDoenca){
        Doenca doenca = doencaRepository.findById(IdDoenca).orElseThrow(() -> new RecursoNaoEncontradoException("ID nao encontrado"));
        doencaRepository.delete(doenca);
    }

    private Doenca toEntity(DoencaRequestDTO dto){
        Doenca doenca  = new Doenca();

        doenca.setIdDoenca(dto.idDoenca());
        doenca.setDescricaoDoenca(dto.descricaoDoenca());
        doenca.setNomeDoenca(dto.nomeDoenca());
        doenca.setHereditaria(dto.hereditaria());

        return doenca;
    }

    public DoencaResponseDTO salvar(DoencaRequestDTO request){
        Doenca doenca = toEntity(request);
        Doenca salvo = doencaRepository.save(doenca);
        return toResponseDTO(salvo);
    }

    public Doenca buscarId(Long IdDoenca) {
        return doencaRepository.findById(IdDoenca).orElseThrow(() -> new RecursoNaoEncontradoException("ID nao encontrado"));

    }
    public DoencaResponseDTO atualizar(Long IdDoenca, DoencaRequestDTO request){
        Doenca doencaExistente = buscarId(IdDoenca);

        doencaExistente.setIdDoenca(request.idDoenca());
        doencaExistente.setDescricaoDoenca(request.descricaoDoenca());
        doencaExistente.setNomeDoenca(request.nomeDoenca());
        doencaExistente.setHereditaria(request.hereditaria());

        Doenca atualizado = doencaRepository.save(doencaExistente);
        return toResponseDTO(atualizado);
    }
}
