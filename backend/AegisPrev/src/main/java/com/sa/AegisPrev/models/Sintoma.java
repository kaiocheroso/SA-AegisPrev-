package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToMany
    private List<Paciente> paciente;
}
