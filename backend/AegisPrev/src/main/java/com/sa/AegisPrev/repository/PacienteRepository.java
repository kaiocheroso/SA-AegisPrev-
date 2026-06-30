package com.sa.AegisPrev.repository;

import com.sa.AegisPrev.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByNomePacienteContainingIgnoreCase (String nomePaciente);

    Optional<Paciente> findByCpfPaciente (String cpfPaciente);

    List<Paciente> findByDataNascimento (LocalDate dataNascimento);

    boolean existsByCpfPaciente(String cpfPaciente);
}
