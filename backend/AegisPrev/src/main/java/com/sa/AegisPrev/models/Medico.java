package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;

    private String nome;
    private String sexo;
    private Integer idade;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;

    @OneToOne
    private Usuario usuario;
}
