package com.sa.AegisPrev.controller;

import com.sa.AegisPrev.DTO.ConsultaResponseDTO;
import com.sa.AegisPrev.DTO.EstatisticaDoenca;
import com.sa.AegisPrev.DTO.EstatisticaMedicoDTO;
import com.sa.AegisPrev.service.AdminService;
import com.sa.AegisPrev.service.ConsultaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService; 

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard/top-doencas")
    public List<EstatisticaDoenca> top10Doencas(){
        return adminService.top10Doencas();
    }

    @GetMapping("/dashboard/top-medicos")
    List<EstatisticaMedicoDTO> rankingMedicos(){
        return adminService.rankingMedicos();
    }

    @PutMapping("/usuario/{id}/remover-admin")
    public void removerAdmin(@PathVariable Long id) {
        adminService.removerAdmin(id);
    }

    @PutMapping("/usuario/{id}/admin-semana")
    public void adminUmaSemana(@PathVariable Long id) {
        adminService.tornarAdminPorUmaSemana(id);
    }

    @PutMapping("/usuario/{id}/admin-mes")
    public void adminUmMes(@PathVariable Long id) {
        adminService.tornarAdminPorUmMes(id);
    }

    @PutMapping("/usuario/{id}/admin-permanente")
    public void adminPermanente(@PathVariable Long id) {
        adminService.tornarAdminPermanente(id);
    }

}
