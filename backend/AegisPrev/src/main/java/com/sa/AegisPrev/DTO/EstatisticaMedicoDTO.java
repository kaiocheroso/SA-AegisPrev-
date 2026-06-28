package com.sa.AegisPrev.DTO;

public record EstatisticaMedicoDTO(
        Long idMedico,
        String nomeMedico,
        long totalConsultas,
        double porcentagem
) {}
