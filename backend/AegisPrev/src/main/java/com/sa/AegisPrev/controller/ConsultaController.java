package com.sa.AegisPrev.controller;

import com.sa.AegisPrev.DTO.ConsultaRequest;
import com.sa.AegisPrev.DTO.ConsultaResponseDTO;
import com.sa.AegisPrev.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping
    public List<ConsultaResponseDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ConsultaResponseDTO pegarId(@Valid @PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public ConsultaResponseDTO cadastrar(@Valid @RequestBody ConsultaRequest dto){
        return service.salvar(dto);
    }


}
