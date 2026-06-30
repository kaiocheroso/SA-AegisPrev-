package com.sa.AegisPrev.repository;

import com.sa.AegisPrev.models.Consulta;
import com.sa.AegisPrev.models.Medico;
import com.sa.AegisPrev.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByMedicoIdMedico(Long idMedico);
    List<Consulta> findByMedico(Medico medico);
    List<Paciente> findByPacienteIdPaciente(Long idPaciente);
    List<Consulta> findByPaciente(Paciente paciente);

    @Query("""
            SELECT c.medico.idMedico, c.medico.nome, COUNT(c)
            FROM Consulta c
            GROUP BY c.medico.idMedico, c.medico.nome
            ORDER BY COUNT(c) DESC
        """)
    List<Object[]> rankingMedicos();
}
