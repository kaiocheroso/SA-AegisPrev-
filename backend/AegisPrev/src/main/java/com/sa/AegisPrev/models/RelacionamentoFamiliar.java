package com.sa.AegisPrev.models;

import jakarta.persistence.*;

@Entity
public class RelacionamentoFamiliar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFamiliar;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Paciente familiar;

    @Enumerated(EnumType.STRING)
    private GrauParentesco grauParentesco;
    //classe para a parte de doencas hereditarias
}
