package com.sa.AegisPrev.DTO;

public record EstatisticaDoenca (
    Long idDoenca,
    String nomeDoenca,
    Long quantidade,
    Double porcentagem
){}
