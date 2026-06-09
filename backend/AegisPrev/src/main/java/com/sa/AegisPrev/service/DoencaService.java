package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.DoencaResponseDTO;
import com.sa.AegisPrev.models.Doenca;
import com.sa.AegisPrev.repository.DoencaRepository;
import org.springframework.stereotype.Service;

@Service
public class DoencaService {
    private final DoencaRepository doencaRepository;

    public DoencaService(DoencaRepository doencaRepository) {
        this.doencaRepository = doencaRepository;
    }

    public DoencaResponseDTO toResponseDTO(Doenca doenca){
        return new DoencaResponseDTO(/*inserir os valores do DTO*/);
    }


}
