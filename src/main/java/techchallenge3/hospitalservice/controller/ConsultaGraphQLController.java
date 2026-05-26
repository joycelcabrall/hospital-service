package techchallenge3.hospitalservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import techchallenge3.hospitalservice.entity.Consulta;
import techchallenge3.hospitalservice.entity.Usuario;
import techchallenge3.hospitalservice.enums.StatusConsulta;
import techchallenge3.hospitalservice.repository.ConsultaRepository;
import techchallenge3.hospitalservice.repository.UsuarioRepository;
import techchallenge3.hospitalservice.service.KafkaProducerService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConsultaGraphQLController {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final KafkaProducerService kafkaProducerService;

    @QueryMapping
    public List<Consulta> consultas() {
        return consultaRepository.findAll();
    }

    @QueryMapping
    public Consulta consultaPorId(@Argument Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    @MutationMapping
    public Consulta criarConsulta(
            @Argument String dataHora,
            @Argument String especialidade,
            @Argument String observacoes,
            @Argument String status,
            @Argument Long medicoId,
            @Argument Long enfermeiroId,
            @Argument Long pacienteId
    ) {
        Usuario medico = buscarUsuario(medicoId, "Médico não encontrado");
        Usuario enfermeiro = buscarUsuario(enfermeiroId, "Enfermeiro não encontrado");
        Usuario paciente = buscarUsuario(pacienteId, "Paciente não encontrado");

        Consulta consulta = new Consulta();

        preencherConsulta(
                consulta,
                dataHora,
                especialidade,
                observacoes,
                status,
                medico,
                enfermeiro,
                paciente
        );

        Consulta consultaSalva = consultaRepository.save(consulta);

        kafkaProducerService.enviarMensagem(
                "Consulta criada - ID: " + consultaSalva.getId()
                        + ", Paciente: " + paciente.getNome()
                        + ", Especialidade: " + consultaSalva.getEspecialidade()
        );

        return consultaSalva;
    }

    @MutationMapping
    public Consulta editarConsulta(
            @Argument Long id,
            @Argument String dataHora,
            @Argument String especialidade,
            @Argument String observacoes,
            @Argument String status,
            @Argument Long medicoId,
            @Argument Long enfermeiroId,
            @Argument Long pacienteId
    ) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        Usuario medico = buscarUsuario(medicoId, "Médico não encontrado");
        Usuario enfermeiro = buscarUsuario(enfermeiroId, "Enfermeiro não encontrado");
        Usuario paciente = buscarUsuario(pacienteId, "Paciente não encontrado");

        preencherConsulta(
                consulta,
                dataHora,
                especialidade,
                observacoes,
                status,
                medico,
                enfermeiro,
                paciente
        );

        Consulta consultaSalva = consultaRepository.save(consulta);

        kafkaProducerService.enviarMensagem(
                "Consulta editada - ID: " + consultaSalva.getId()
                        + ", Paciente: " + paciente.getNome()
                        + ", Especialidade: " + consultaSalva.getEspecialidade()
        );

        return consultaSalva;
    }

    @MutationMapping
    public String excluirConsulta(@Argument Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        kafkaProducerService.enviarMensagem(
                "Consulta excluída - ID: " + consulta.getId()
                        + ", Especialidade: " + consulta.getEspecialidade()
        );

        consultaRepository.delete(consulta);

        kafkaProducerService.enviarMensagem(
                "Consulta excluída - ID: " + id
        );

        return "Consulta excluída com sucesso";
    }

    private Usuario buscarUsuario(Long id, String mensagemErro) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(mensagemErro));
    }

    private void preencherConsulta(
            Consulta consulta,
            String dataHora,
            String especialidade,
            String observacoes,
            String status,
            Usuario medico,
            Usuario enfermeiro,
            Usuario paciente
    ) {
        consulta.setDataHora(LocalDateTime.parse(dataHora));
        consulta.setEspecialidade(especialidade);
        consulta.setObservacoes(observacoes);
        consulta.setStatus(StatusConsulta.valueOf(status));
        consulta.setMedico(medico);
        consulta.setEnfermeiro(enfermeiro);
        consulta.setPaciente(paciente);
    }
}