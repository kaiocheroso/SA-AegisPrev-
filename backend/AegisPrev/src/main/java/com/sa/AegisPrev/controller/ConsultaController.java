package com.sa.AegisPrev.controller;

import com.sa.AegisPrev.DTO.ConsultaRequestDTO;
import com.sa.AegisPrev.DTO.ConsultaResponseDTO;
import com.sa.AegisPrev.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping("/admin/consultas")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ConsultaResponseDTO> listarAdmin(){
        return service.listarTodasComoAdmin();
    }

    @GetMapping
    @PreAuthorize("hasRole('MEDICO')")
    public List<ConsultaResponseDTO> listarMedico(){
        return service.listarDoMedicoLogado();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ConsultaResponseDTO pegarId(@Valid @PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ConsultaResponseDTO cadastrar(@Valid @RequestBody ConsultaRequestDTO dto){
        return service.salvar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ConsultaResponseDTO atualizar(@Valid @PathVariable Long id, @Valid @RequestBody ConsultaRequestDTO dto){
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletar(@Valid @PathVariable Long id){
        service.deletar(id);
    }
}
