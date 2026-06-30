package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Medico;
import com.sa.AegisPrev.models.Paciente;
import com.sa.AegisPrev.models.Papel;
import com.sa.AegisPrev.models.Usuario;
import com.sa.AegisPrev.repository.MedicoRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    //Dica do dia, se estiver funcionando, NÃO MEXA

    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    public MedicoService(MedicoRepository medicoRepository, PasswordEncoder passwordEncoder) {
        this.medicoRepository = medicoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private MedicoResponseDTO toResponseDTO(Medico medico) {

        return new MedicoResponseDTO(
                medico.getIdMedico(),
                medico.getNome(),
                medico.getSexo(),
                medico.getIdade(),
                medico.getUsuario().getEmail(),
                medico.getConsultas()
                        .stream().map(consulta -> new ConsultaResumoDTO(
                                    consulta.getIdConsulta(),
                                    consulta.getMedico().getIdMedico(),
                                    consulta.getPaciente().getIdPaciente(),
                                    consulta.getDataConsulta(),
                                    consulta.getDoencas().stream().map(doenca -> new DoencaResumoDTO(
                                            doenca.getIdDoenca(),
                                            doenca.getNomeDoenca()
                                    )).toList()
                        )).toList()
        );
    }

    private Medico toEntity(MedicoRequestDTO dto){
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuario.setPapeis(Papel.ROLE_MEDICO);

        Medico medico = new Medico();

        medico.setNome(dto.nome());
        medico.setSexo(dto.sexo());
        medico.setIdade(dto.idade());

        medico.setUsuario(usuario);
        return medico;
    }

    private void validarAcessoMedico(Medico medico) {

        Usuario usuario = UsuarioService.obterUsuarioLogado();

        if (usuario.getPapeis() == Papel.ROLE_ADMIN) {
            return;
        }

        if (!medico.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new AccessDeniedException("Acesso negado");
        }
    }

    public List<MedicoResponseDTO> listar(String email, String nome) {
        List<Medico> medicos;

        if (email != null){
            medicos = buscarPorEmail(email);
        } else if (nome != null) {
            medicos = buscarPorNome(nome);
        } else {
            medicos = medicoRepository.findAll();
        }

        return medicos.stream().map(this::toResponseDTO).toList();
    }

    public MedicoResponseDTO buscarPorId(Long idMedico){
        Medico medico = buscarId(idMedico);
        validarAcessoMedico(medico);
        return toResponseDTO(medico);
    }

    public Medico buscarId(Long idMedico){
        //metodo para buscar por id e retorna um medico.
        return medicoRepository.findById(idMedico).orElseThrow(() -> new RecursoNaoEncontradoException("ID nao encontrado"));
    }

    public List<Medico> buscarPorEmail(String email){
        //deveria ser optional, mas foi o que deu para colocar no listar();
         return medicoRepository.findByUsuarioEmail(email);
    }

    public List<Medico> buscarPorNome(String nome){
        return medicoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public MedicoResponseDTO salvar(MedicoRequestDTO request){
        Medico medico = toEntity(request);
        Medico salvo = medicoRepository.save(medico);
        return toResponseDTO(salvo);
    }

    public MedicoResponseDTO atualizar(Long idMedico, MedicoRequestDTO request){
        //nao e necessario mexer no usuarios e nem em consulta na parte da atualizacao.
        //iremos criar um mwetodo apenas para atualiazr a roles do medico.
        Medico medicoExistente = buscarId(idMedico);

        validarAcessoMedico(medicoExistente);

        medicoExistente.setNome(request.nome());
        medicoExistente.setSexo(request.sexo());
        medicoExistente.setIdade(request.idade());

        Medico atualizado = medicoRepository.save(medicoExistente);
        return toResponseDTO(atualizado);
    }


    public void deletar(Long idMedico){
        Medico medico = buscarId(idMedico);
        medicoRepository.delete(medico);
    }

    /// PRECISAMOS FAZER O DISABLE DO CSRF NO SECURITY.
    /// COLOCAR UM DB NO APPLICATION PROPERTIES.
    /// FAZER COM QUE O RELACIONAMENTO ENTRE PACIENTE E MEDICO SE TORNE =
    ///     MEDICO -> CONSULTA <- PACIENTE
    /// com isto , provavelmente vai voltar a funcionar o codigo.
    /// esse projeto vai ficar bom rapaziada :)
}
