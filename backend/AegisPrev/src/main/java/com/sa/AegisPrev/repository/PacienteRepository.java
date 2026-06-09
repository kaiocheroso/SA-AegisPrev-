package com.sa.AegisPrev.repository;

import com.sa.AegisPrev.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
