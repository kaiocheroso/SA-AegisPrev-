package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    @JoinTable(
            name = "doenca_sintoma",
            joinColumns = @JoinColumn(name = "id_doenca"),
            inverseJoinColumns = @JoinColumn(name = "id_sintoma")
    )
    private List<Sintoma> sintomas = new ArrayList<>();

    @ManyToMany(mappedBy = "doencas")
    private List<Consulta> consultas = new ArrayList<>();
}
