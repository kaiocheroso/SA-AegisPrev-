package com.sa.AegisPrev.controller;

import com.sa.AegisPrev.DTO.DoencaRequestDTO;
import com.sa.AegisPrev.DTO.DoencaResponseDTO;
import com.sa.AegisPrev.service.DoencaService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doencas")
@PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
public class DoencaController {

    private final DoencaService service;

    public DoencaController(DoencaService service) {
        this.service = service;
    }

    @GetMapping
    public List<DoencaResponseDTO> listar(String nomeDoenca, Boolean isHereditaria){
        return service.listar(nomeDoenca, isHereditaria);
    }

    @GetMapping("/{id}")
    public DoencaResponseDTO pegarId(@Valid @PathVariable Long id){
        return service.pegarPorId(id);
    }

    @PostMapping
    public DoencaResponseDTO salvar(@Valid @RequestBody DoencaRequestDTO dto) {
        return service.salvar(dto);
    }

    @PutMapping("/{id}")
    public DoencaResponseDTO atualizar(@Valid @PathVariable Long id, @Valid @RequestBody DoencaRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id) {
        service.deletar(id);
    }
}