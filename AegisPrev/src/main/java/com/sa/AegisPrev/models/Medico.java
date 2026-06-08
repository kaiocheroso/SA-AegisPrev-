package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;

    private String email;
    private String password;


    @OneToMany
    @JoinColumn(name = "idUsuario")
    private Paciente paciente;
}
