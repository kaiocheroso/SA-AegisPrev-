package com.sa.AegisPrev.controller;

import com.sa.AegisPrev.DTO.PacienteRequestDTO;
import com.sa.AegisPrev.DTO.PacienteResponseDTO;
import com.sa.AegisPrev.service.PacienteService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<PacienteResponseDTO> listar(
            @RequestParam(required = false) String nomePaciente,
            @RequestParam(required = false) String cpfPaciente
    ){
        return service.listar(nomePaciente, cpfPaciente);
    }

    @GetMapping("/{idPaciente}")
    public PacienteResponseDTO buscarPorId(@Valid @PathVariable Long idPaciente){
        return service.buscarPorId(idPaciente);
    }

    @PostMapping
    public PacienteResponseDTO cadastrarPaciente(@Valid @RequestBody PacienteRequestDTO dto){
        return service.cadastrarPaciente(dto);
    }

    @PutMapping("/{idPaciente}")
    public PacienteResponseDTO atualizarPaciente(@Valid @PathVariable Long idPaciente, @Valid @RequestBody PacienteRequestDTO dto){
        return service.atualizarPaciente(idPaciente, dto);
    }

    @DeleteMapping("/{idPaciente}")
    public void deletarPaciente(@Valid @PathVariable Long idPaciente){
        service.deletar(idPaciente);
    }
}
