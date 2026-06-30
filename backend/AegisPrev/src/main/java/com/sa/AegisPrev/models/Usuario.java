package com.sa.AegisPrev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Papel papeis;

    private LocalDateTime adminDate; //Data de até quando é ADMIN
}
