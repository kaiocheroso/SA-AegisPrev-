package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.DoencaResponseDTO;
import com.sa.AegisPrev.DTO.SintomaResumoDTO;
import com.sa.AegisPrev.models.Doenca;
import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Sintoma;
import com.sa.AegisPrev.repository.DoencaRepository;
import com.sa.AegisPrev.repository.SintomaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoencaService {
    private final DoencaRepository doencaRepository;
    private final SintomaRepository sintomaRepository;

    public DoencaService(DoencaRepository doencaRepository, SintomaRepository sintomaRepository) {
        this.doencaRepository = doencaRepository;
        this.sintomaRepository = sintomaRepository;
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

        doenca.setDescricaoDoenca(dto.descricaoDoenca());
        doenca.setNomeDoenca(dto.nomeDoenca());
        doenca.setHereditaria(dto.hereditaria());

        if (dto.idSintomas() != null && !dto.idSintomas().isEmpty()) {
            doenca.setSintomas(sintomaRepository.findAllById(dto.idSintomas()));
        }

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

        doencaExistente.setDescricaoDoenca(request.descricaoDoenca());
        doencaExistente.setNomeDoenca(request.nomeDoenca());
        doencaExistente.setHereditaria(request.hereditaria());

        if (request.idSintomas() != null && !request.idSintomas().isEmpty()) {
            doencaExistente.setSintomas(
                    sintomaRepository.findAllById(request.idSintomas())
            );
        }

        Doenca atualizado = doencaRepository.save(doencaExistente);
        return toResponseDTO(atualizado);
    }

    public DoencaResponseDTO pegarPorId(Long id){
        return toResponseDTO(buscarId(id));
    }

    public List<DoencaResponseDTO> listar(String nomeDoenca, Boolean isHereditaria){
        List<Doenca> doencas;

        if (nomeDoenca != null){
            doencas = buscarNomeDoenca(nomeDoenca);
        } else if (Boolean.TRUE.equals(isHereditaria)) {
            doencas = buscarPorHereditaria();
        }else {
            doencas = doencaRepository.findAll();
        }
        return doencas.stream().map(this::toResponseDTO).toList();
    }

    private List<Doenca> buscarNomeDoenca(String nomeDoenca){
        return doencaRepository.findByNomeDoencaContainingIgnoreCase(nomeDoenca);
    }

    private List<Doenca> buscarPorHereditaria(){
        return doencaRepository.findByHereditaria(true);
    }
}
