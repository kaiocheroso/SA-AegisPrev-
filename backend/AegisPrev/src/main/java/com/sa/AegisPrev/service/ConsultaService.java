package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.*;
import com.sa.AegisPrev.repository.ConsultaRepository;
import com.sa.AegisPrev.repository.DoencaRepository;
import com.sa.AegisPrev.repository.MedicoRepository;
import com.sa.AegisPrev.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {
    /// RAPAZIADA, TEREMOS QUE CRIAR O SINTOMA e fazer o resultado trazer com lista de possiveis doencas
    /// teremos que pegar os sintomas pelo paciente, e fazer com que as possiveis doencas aparecam

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final DoencaRepository doencaRepository;


    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, DoencaRepository doencaRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.doencaRepository = doencaRepository;
    }

    private ConsultaResponseDTO toResponse(Consulta consulta){
        return new ConsultaResponseDTO(
                consulta.getIdConsulta(),
                new MedicoResumoDTO(
                        consulta.getMedico().getIdMedico(),
                        consulta.getMedico().getNome()
                ),
                new PacienteResumoDTO(
                        consulta.getPaciente().getIdPaciente(),
                        consulta.getPaciente().getNomePaciente(),
                        consulta.getPaciente().getCpfPaciente()
                ),
                consulta.getDataConsulta(),
                consulta.getDescricao(),
                consulta.getDoencas().stream().map(doenca -> new DoencaResumoDTO(
                        doenca.getIdDoenca(),
                        doenca.getNomeDoenca()
                )).toList(),
                calcularPrevisoes(consulta.getPaciente())
        );
    }

    public List<ConsultaResponseDTO> listar(){
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ConsultaResponseDTO buscarPorId(Long id){
        return toResponse(pegarId(id));
    }

    private Consulta pegarId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("ID não encontrado"));
    }

    public ConsultaResponseDTO salvar(ConsultaRequestDTO dto){
        Medico medico = medicoRepository.findById(dto.idMedico()).orElseThrow(() -> new RecursoNaoEncontradoException("ID do medico nao encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.idPaciente()).orElseThrow(() -> new RecursoNaoEncontradoException("ID do paciente nao encontrado"));
        Consulta consulta = new Consulta();

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDataConsulta(LocalDateTime.now()); //talvez tenha que apagar o date do consultaRequest
        consulta.setDescricao(dto.descricao());

        List<Doenca> previsoes = preverDoencas(paciente);
        consulta.setDoencas(previsoes);

        Consulta salvo = repository.save(consulta);
        return toResponse(salvo);
    }

    public ConsultaResponseDTO atualizar(Long idConsulta, ConsultaRequestDTO dto){

        Consulta consulta = pegarId(idConsulta);

        Medico medico = medicoRepository.findById(dto.idMedico())
                .orElseThrow(() -> new RecursoNaoEncontradoException("ID do medico nao encontrado"));

        Paciente paciente = pacienteRepository.findById(dto.idPaciente())
                .orElseThrow(() -> new RecursoNaoEncontradoException("ID do paciente nao encontrado"));

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDescricao(dto.descricao());

        consulta.setDoencas(preverDoencas(paciente));

        Consulta atualizada = repository.save(consulta);

        return toResponse(atualizada);
    }

    public void deletar(Long idConsulta){
        Consulta consulta = pegarId(idConsulta);
        repository.delete(consulta);
    }

    private List<Doenca> preverDoencas(Paciente paciente){

        List<Sintoma> sintomasPaciente = paciente.getSintomas();

        return doencaRepository.findAll()
                .stream()
                .filter(doenca ->
                        doenca.getSintomas()
                                .stream()
                                .anyMatch(sintomasPaciente::contains)
                )
                .toList();
    }

    private List<DoencaPrevistaDTO> calcularPrevisoes(Paciente paciente){

        List<Sintoma> sintomasPaciente = paciente.getSintomas();

        return doencaRepository.findAll()
                .stream()
                .map(doenca -> {

                    long coincidencias = doenca.getSintomas()
                            .stream()
                            .filter(sintomasPaciente::contains)
                            .count();

                    double compatibilidade = 0;

                    if (!sintomasPaciente.isEmpty()) {
                        compatibilidade =
                                ((double) coincidencias / sintomasPaciente.size()) * 100;
                    }

                    return new DoencaPrevistaDTO(
                            doenca.getIdDoenca(),
                            doenca.getNomeDoenca(),
                            compatibilidade
                    );
                })
                .filter(dto -> dto.compatibilidade() > 0)
                .sorted((a, b) ->
                        Double.compare(
                                b.compatibilidade(),
                                a.compatibilidade()))
                .toList();
    }

}
