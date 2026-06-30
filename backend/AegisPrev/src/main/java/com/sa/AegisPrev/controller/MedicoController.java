package com.sa.AegisPrev.controller;

import com.sa.AegisPrev.DTO.MedicoRequestDTO;
import com.sa.AegisPrev.DTO.MedicoResponseDTO;
import com.sa.AegisPrev.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Medico" , description = "Rotas para o gerenciamento do medico")
@RestController
@RequestMapping("/medicos")
public class MedicoController {
    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os medicos, também pode adicionar filtros")
    @GetMapping
    public List<MedicoResponseDTO> listar(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nome
        ){
        return medicoService.listar(email, nome);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @Operation(summary = "Buscar medico por ID")
    @GetMapping("/{idMedico}")
    public MedicoResponseDTO buscarPorId(@Valid @PathVariable Long idMedico){
        return medicoService.buscarPorId(idMedico);
    }

    @PostMapping
    public MedicoResponseDTO criar(@Valid @RequestBody MedicoRequestDTO dto){
        return medicoService.salvar(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @PutMapping("/{idMedico}")
    public MedicoResponseDTO atualizar(@Valid @PathVariable Long idMedico, @Valid @RequestBody MedicoRequestDTO dto){
        return medicoService.atualizar(idMedico, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idMedico}")
    public void deletar(@Valid @PathVariable Long idMedico){
        medicoService.deletar(idMedico);
    }
}
