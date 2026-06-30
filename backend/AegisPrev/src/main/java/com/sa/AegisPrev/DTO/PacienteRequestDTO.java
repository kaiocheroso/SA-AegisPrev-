package com.sa.AegisPrev.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record PacienteRequestDTO (

        @NotBlank(message = "O paciente deve conter um nome ")
        String nomePaciente,
        @NotBlank(message = "CPF nao pode estar vazio")
        @CPF(message = "O CPF informado é inválido")
        String cpfPaciente,
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,
        List<Long> idsSintomas,
        List<Long> idsDoencas
){
}
