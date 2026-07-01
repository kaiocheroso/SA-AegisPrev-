package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.*;
import com.sa.AegisPrev.repository.*;
import com.sa.AegisPrev.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final SintomaRepository sintomaRepository;

    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, DoencaRepository doencaRepository, SintomaRepository sintomaRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.doencaRepository = doencaRepository;
        this.sintomaRepository = sintomaRepository;
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
                consulta.getSintomas().stream().map(sintoma -> new SintomaResumoDTO(
                        sintoma.getIdSintoma(),
                        sintoma.getNomeSintoma()
                )).toList(),

                consulta.getDoencas().stream().map(doenca -> new DoencaResumoDTO(
                        doenca.getIdDoenca(),
                        doenca.getNomeDoenca()
                )).toList(),

                calcularPrevisoes(consulta)
        );
    }

    private Usuario obterUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsuario();
        }

        throw new RecursoNaoEncontradoException("Usuário não autenticado");
    }

    private void validarAcessoConsulta(Consulta consulta) {

        Usuario usuario = obterUsuarioLogado();

        if (usuario.getPapeis() == Papel.ROLE_ADMIN) {
            return; // admin pode tudo
        }

        Medico medico = medicoRepository
                .findByUsuarioIdUsuario(usuario.getIdUsuario())
                .orElseThrow();

        if (!consulta.getMedico().getIdMedico().equals(medico.getIdMedico())) {
            throw new RecursoNaoEncontradoException("Acesso negado");
        }
    }

    public List<ConsultaResponseDTO> listarTodasComoAdmin() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ConsultaResponseDTO> listarDoMedicoLogado() {

        Usuario usuario = obterUsuarioLogado();

        Medico medico = medicoRepository
                .findByUsuarioIdUsuario(usuario.getIdUsuario())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado"));

        return repository.findByMedico(medico)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ConsultaResponseDTO buscarPorId(Long id){
        Consulta consulta = pegarId(id);
        validarAcessoConsulta(consulta);
        return toResponse(consulta);
    }

    private Consulta pegarId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("ID não encontrado"));
    }

    public ConsultaResponseDTO salvar(ConsultaRequestDTO dto){
        Usuario usuario = obterUsuarioLogado();

        Medico medico = medicoRepository.findByUsuarioIdUsuario(usuario.getIdUsuario()).orElseThrow(() -> new RecursoNaoEncontradoException("ID do usuario nao encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.idPaciente()).orElseThrow(() -> new RecursoNaoEncontradoException("ID do paciente nao encontrado"));

        Consulta consulta = new Consulta();

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDataConsulta(LocalDateTime.now()); //talvez tenha que apagar o date do consultaRequest
        consulta.setDescricao(dto.descricao());

        if (dto.sintomasIds() != null && !dto.sintomasIds().isEmpty()) {
            consulta.setSintomas(
                    sintomaRepository.findAllById(dto.sintomasIds())
            );
        }

        List<Doenca> previsoes = preverDoencas(consulta);
        consulta.setDoencas(previsoes);

        Consulta salvo = repository.save(consulta);
        return toResponse(salvo);
    }

    public ConsultaResponseDTO atualizar(Long idConsulta, ConsultaRequestDTO dto){

        Consulta consulta = pegarId(idConsulta);
        validarAcessoConsulta(consulta);

        Medico medico = medicoRepository
                .findByUsuarioIdUsuario(obterUsuarioLogado().getIdUsuario())
                .orElseThrow(() -> new RecursoNaoEncontradoException("ID do medico nao encontrado"));

        Paciente paciente = pacienteRepository.findById(dto.idPaciente())
                .orElseThrow(() -> new RecursoNaoEncontradoException("ID do paciente nao encontrado"));

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDescricao(dto.descricao());

        if (dto.sintomasIds() != null) {
            consulta.setSintomas(
                    sintomaRepository.findAllById(dto.sintomasIds())
            );
        }

        consulta.setDoencas(preverDoencas(consulta));

        Consulta atualizada = repository.save(consulta);

        return toResponse(atualizada);
    }

    public void deletar(Long idConsulta){
        Consulta consulta = pegarId(idConsulta);
        validarAcessoConsulta(consulta);
        repository.delete(consulta);
    }

    private List<Doenca> preverDoencas(Consulta consulta) {

        List<Sintoma> sintomasConsulta =
                consulta.getSintomas() != null ? consulta.getSintomas() : List.of();

        return doencaRepository.findAll()
                .stream()
                .filter(doenca -> calcularCompatibilidade(doenca, sintomasConsulta) >= 70)
                .toList();
    }

    private List<DoencaPrevistaDTO> calcularPrevisoes(Consulta consulta){

        List<Sintoma> sintomasConsulta =
                consulta.getSintomas() != null ? consulta.getSintomas() : List.of();

        return doencaRepository.findAll()
                .stream()
                .map(doenca -> new DoencaPrevistaDTO(
                        doenca.getIdDoenca(),
                        doenca.getNomeDoenca(),
                        calcularCompatibilidade(doenca, sintomasConsulta)
                ))
                .filter(dto -> dto.compatibilidade() >= 10)
                .sorted((a, b) -> Double.compare(b.compatibilidade(), a.compatibilidade()))
                .toList();
    }

    private double calcularCompatibilidade(Doenca doenca, List<Sintoma> sintomasConsulta) {

        if (sintomasConsulta.isEmpty()) {
            return 0;
        }

        long coincidencias = doenca.getSintomas()
                .stream()
                .filter(sintomasConsulta::contains)
                .count();

        return ((double) coincidencias / sintomasConsulta.size()) * 100;
    }

}
