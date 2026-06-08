package com.sa.AegisPrev.repository;

import com.sa.AegisPrev.models.Doenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoencaRepository extends JpaRepository<Doenca, Long> {
    List<Doenca> findByNomeDoencaEqualsIgnoreCase(String nomeDoenca);

}
