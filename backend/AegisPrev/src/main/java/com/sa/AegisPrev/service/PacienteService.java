package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Medico;
import com.sa.AegisPrev.models.Paciente;
import com.sa.AegisPrev.models.Papel;
import com.sa.AegisPrev.models.Usuario;
import com.sa.AegisPrev.repository.MedicoRepository;
import com.sa.AegisPrev.repository.PacienteRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sa.AegisPrev.service.UsuarioService.obterUsuarioLogado;

@Service
public class PacienteService {
    /// TENHA CERTEZA ABSOLUTA DO QUE MUDAR

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public PacienteService(PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    private PacienteResponseDTO toResponseDTO(Paciente paciente){
        return new PacienteResponseDTO(
                paciente.getIdPaciente(),
                paciente.getNomePaciente(),
                paciente.getCpfPaciente(),
                paciente.getDataNascimento(),
                paciente.getSintomas().stream().map(sintoma -> new SintomaResumoDTO(
                        sintoma.getIdSintoma(),
                        sintoma.getNomeSintoma()
                )).toList(),
                paciente.getDoencas().stream().map(doenca -> new DoencaResumoDTO(
                        doenca.getIdDoenca(),
                        doenca.getNomeDoenca()
                )).toList(),
                paciente.getConsultas().stream().map(consulta -> new ConsultaResumoDTO(
                        consulta.getIdConsulta(),
                        consulta.getMedico().getIdMedico(),
                        consulta.getPaciente().getIdPaciente(),
                        consulta.getDataConsulta(),
                        consulta.getDescricao()
                )).toList()
        );
    }

    private Paciente toEntity(PacienteRequestDTO dto){
            Paciente paciente = new Paciente();
            paciente.setNomePaciente(dto.nomePaciente());
            paciente.setCpfPaciente(dto.cpfPaciente());
            paciente.setDataNascimento(dto.dataNascimento());

            return paciente;
    }

    /*private void validarAcessoPaciente(Paciente paciente) {

        Usuario usuario = obterUsuarioLogado();

        if (usuario.getPapeis() == Papel.ROLE_ADMIN) {
            return;
        }

        Medico medico = medicoRepository
                .findByUsuarioIdUsuario(usuario.getIdUsuario())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado"));

        boolean pertenceAoMedico = paciente.getConsultas().stream().anyMatch( c -> c.getMedico().getIdMedico().equals(medico.getIdMedico()));

        if (!pertenceAoMedico){
            throw new AccessDeniedException("Acesso Negado");
        }
    }*/

    public List<PacienteResponseDTO> listar(String nomePaciente, String cpfPaciente){
        List<Paciente> pacientes;

        if (nomePaciente != null){
            pacientes = buscarPorNomePaciente(nomePaciente);
        } else if (cpfPaciente != null) {
            pacientes = List.of(buscarPorCpf(cpfPaciente));
        }else{
            pacientes = pacienteRepository.findAll();
        }

        return pacientes.stream().map(this::toResponseDTO).toList();
    }

    private List<Paciente> buscarPorNomePaciente(String nomePaciente){
        return pacienteRepository.findByNomePacienteContainingIgnoreCase(nomePaciente);
    }

    private Paciente buscarPorCpf(String cpfPaciente){
        return pacienteRepository.findByCpfPacienteContainingIgnoreCase(cpfPaciente).orElseThrow(() -> new RecursoNaoEncontradoException("CPF nao encontrado"));
    }

    public PacienteResponseDTO buscarPorId(Long idPaciente){
        Paciente paciente = buscarId(idPaciente);

        return toResponseDTO(paciente);
    }

    public PacienteResponseDTO cadastrarPaciente(PacienteRequestDTO dto){
        Paciente paciente = toEntity(dto);
        Paciente salvo = pacienteRepository.save(paciente);
        return toResponseDTO(salvo);
    }

    public PacienteResponseDTO atualizarPaciente(Long idPaciente, PacienteRequestDTO dto){
        Paciente pacienteExistente = buscarId(idPaciente);

        pacienteExistente.setNomePaciente(dto.nomePaciente());
        pacienteExistente.setCpfPaciente(dto.cpfPaciente());
        pacienteExistente.setDataNascimento(dto.dataNascimento());

        Paciente atualizado = pacienteRepository.save(pacienteExistente);

        return toResponseDTO(atualizado);
    }

    public void deletar(Long idPaciente){
        Paciente paciente = buscarId(idPaciente);
        pacienteRepository.delete(paciente);
    }

    private Paciente buscarId(Long idPaciente){
        return pacienteRepository.findById(idPaciente).orElseThrow(() -> new RecursoNaoEncontradoException("ID do paciente nao encontrado"));
    }

}
