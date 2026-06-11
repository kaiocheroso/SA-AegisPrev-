package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Doenca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoenca;

    private String nomeDoenca;
    private String descricaoDoenca;

    private Boolean hereditaria;

    @ManyToMany
    private List<Sintoma> sintomas;

    @ManyToMany
    private List<Paciente> pacientes;

    @ManyToMany
    private List<Consulta> consultas;
}
