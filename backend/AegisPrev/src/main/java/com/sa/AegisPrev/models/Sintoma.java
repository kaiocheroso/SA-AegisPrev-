package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Sintoma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSintoma;

    private String nomeSintoma;
    private String descricaoSintoma;

    @ManyToMany (mappedBy = "sintomas")
    private List<Paciente> paciente = new ArrayList<>();

    @ManyToMany
    private List<Doenca> doencas = new ArrayList<>();
}
