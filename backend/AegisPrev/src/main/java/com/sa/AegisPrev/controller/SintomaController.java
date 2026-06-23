package com.sa.AegisPrev.controller;

import com.sa.AegisPrev.DTO.SintomaRequestDTO;
import com.sa.AegisPrev.DTO.SintomaResponseDTO;
import com.sa.AegisPrev.service.SintomaService;
import jakarta.validation.Valid;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sintomas")
public class SintomaController {
    private final SintomaService service;

    public SintomaController(SintomaService service) {
        this.service = service;
    }

    @GetMapping
    public List<SintomaResponseDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public SintomaResponseDTO buscarPorId( @Valid @PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public SintomaResponseDTO cadastrar(@Valid @RequestBody SintomaRequestDTO dto){
        return service.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public SintomaResponseDTO atualizar(@Valid @PathVariable Long id, @Valid @RequestBody SintomaRequestDTO dto){
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id){
        service.deletar(id);
    }
}
