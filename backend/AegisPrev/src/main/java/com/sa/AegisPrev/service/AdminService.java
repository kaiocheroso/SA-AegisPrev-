package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.EstatisticaDoenca;
import com.sa.AegisPrev.DTO.EstatisticaMedicoDTO;
import com.sa.AegisPrev.models.*;
import com.sa.AegisPrev.repository.ConsultaRepository;
import com.sa.AegisPrev.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final UsuarioRepository usuarioRepository;
    private final ConsultaRepository consultaRepository;


    public AdminService(UsuarioRepository usuarioRepository, ConsultaRepository consultaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.consultaRepository = consultaRepository;
    }

    public void tornarAdmin(Long idUsuario, LocalDateTime dataFim){
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow();

        usuario.setPapeis(Papel.ROLE_ADMIN);
        usuario.setAdminDate(dataFim);

        usuarioRepository.save(usuario);

    }

    public void removerAdmin(Long idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow();

        usuario.setPapeis(Papel.ROLE_MEDICO);
        usuario.setAdminDate(null);

        usuarioRepository.save(usuario);
    }

    public void tornarAdminPorUmaSemana(Long id){
        tornarAdmin(id, LocalDateTime.now().plusWeeks(1));
    }

    public void tornarAdminPorUmMes(Long id){
        tornarAdmin(id, LocalDateTime.now().plusMonths(1));
    }

    public void tornarAdminPermanente(Long id){
        tornarAdmin(id, null);
    }

    public List<EstatisticaDoenca> top10Doencas() {

        List<Consulta> consultas = consultaRepository.findAll();

        long totalDiagnosticos = consultas.stream()
                .flatMap(c -> c.getDoencas().stream())
                .count();

        return consultas.stream()

                .flatMap(c -> c.getDoencas().stream())

                .collect(Collectors.groupingBy(
                        Doenca::getNomeDoenca,
                        Collectors.counting()
                ))

                .entrySet()

                .stream()

                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())

                .limit(10)

                .map(entry -> {

                    double porcentagem = totalDiagnosticos == 0
                            ? 0
                            : (entry.getValue() * 100.0) / totalDiagnosticos;

                    return new EstatisticaDoenca(
                            null,
                            entry.getKey(),
                            entry.getValue(),
                            porcentagem
                    );
                })

                .toList();
    }

    public List<EstatisticaMedicoDTO> rankingMedicos() {

        long totalConsultas = consultaRepository.count();

        return consultaRepository.rankingMedicos()
                .stream()
                .limit(10)
                .map(obj -> {

                    Long id = (Long) obj[0];
                    String nome = (String) obj[1];
                    Long quantidade = (Long) obj[2];

                    double porcentagem = totalConsultas == 0
                            ? 0
                            : quantidade * 100.0 / totalConsultas;

                    return new EstatisticaMedicoDTO(
                            id,
                            nome,
                            quantidade,
                            porcentagem
                    );
                })
                .toList();
    }

}
