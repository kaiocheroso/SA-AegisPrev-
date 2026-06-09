package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;

    private String nomePaciente;
    private String cpfPaciente;

    private LocalDate dataNascimento;

    @ManyToMany
    @JoinTable(name = "paciente_sintomas",
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "sintoma_id")
    )
    private List<Sintoma> sintomas;

    @ManyToMany(mappedBy = "pacientes")
    private List<Doenca> doencas;

    @OneToMany(mappedBy = "paciente")
    private List<RelacionamentoFamiliar> familiares;
}
