package com.sa.AegisPrev.repository;

import com.sa.AegisPrev.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByEmail(String email);

    List<Medico> findByNomeEqualsIgnoreCase(String nome);
}
