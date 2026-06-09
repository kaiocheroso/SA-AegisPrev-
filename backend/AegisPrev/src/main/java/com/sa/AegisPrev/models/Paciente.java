package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nome;
    private String cpf;

    @ManyToMany
    private List<Sintoma> sintomas;

    @ManyToMany
    private List<Doenca> doencas;

    @ManyToOne
    private Medico medico;

}
