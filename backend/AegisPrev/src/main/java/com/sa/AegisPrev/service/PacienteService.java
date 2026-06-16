package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.*;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Paciente;
import com.sa.AegisPrev.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    /// TENHA CERTEZA ABSOLUTA DO QUE MUDAR

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
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
        return toResponseDTO(buscarId(idPaciente));
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
