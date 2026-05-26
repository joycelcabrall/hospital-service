package techchallenge3.hospitalservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import techchallenge3.hospitalservice.dto.ConsultaRequest;
import techchallenge3.hospitalservice.entity.Consulta;
import techchallenge3.hospitalservice.entity.Usuario;
import techchallenge3.hospitalservice.repository.ConsultaRepository;
import techchallenge3.hospitalservice.repository.UsuarioRepository;
import techchallenge3.hospitalservice.service.KafkaProducerService;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping
    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Consulta buscarConsultaPorId(@PathVariable Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    @PostMapping
    public Consulta salvarConsulta(@RequestBody ConsultaRequest request) {

        Usuario medico = buscarUsuario(request.getMedicoId(), "Médico não encontrado");
        Usuario enfermeiro = buscarUsuario(request.getEnfermeiroId(), "Enfermeiro não encontrado");
        Usuario paciente = buscarUsuario(request.getPacienteId(), "Paciente não encontrado");

        Consulta consulta = new Consulta();
        preencherConsulta(consulta, request, medico, enfermeiro, paciente);

        Consulta consultaSalva = consultaRepository.save(consulta);

        kafkaProducerService.enviarMensagem(
                "Consulta criada | ID: " + consultaSalva.getId()
                        + " | Paciente: " + paciente.getNome()
                        + " | Especialidade: " + consultaSalva.getEspecialidade()
                        + " | Data/Hora: " + consultaSalva.getDataHora()
        );

        return consultaSalva;
    }

    @PutMapping("/{id}")
    public Consulta editarConsulta(@PathVariable Long id, @RequestBody ConsultaRequest request) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        Usuario medico = buscarUsuario(request.getMedicoId(), "Médico não encontrado");
        Usuario enfermeiro = buscarUsuario(request.getEnfermeiroId(), "Enfermeiro não encontrado");
        Usuario paciente = buscarUsuario(request.getPacienteId(), "Paciente não encontrado");

        preencherConsulta(consulta, request, medico, enfermeiro, paciente);

        Consulta consultaAtualizada = consultaRepository.save(consulta);

        kafkaProducerService.enviarMensagem(
                "Consulta editada | ID: " + consultaAtualizada.getId()
                        + " | Paciente: " + paciente.getNome()
                        + " | Especialidade: " + consultaAtualizada.getEspecialidade()
                        + " | Data/Hora: " + consultaAtualizada.getDataHora()
        );

        return consultaAtualizada;
    }

    @DeleteMapping("/{id}")
    public void deletarConsulta(@PathVariable Long id) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consultaRepository.delete(consulta);
    }

    private Usuario buscarUsuario(Long id, String mensagemErro) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(mensagemErro));
    }

    private void preencherConsulta(
            Consulta consulta,
            ConsultaRequest request,
            Usuario medico,
            Usuario enfermeiro,
            Usuario paciente
    ) {
        consulta.setDataHora(request.getDataHora());
        consulta.setEspecialidade(request.getEspecialidade());
        consulta.setObservacoes(request.getObservacoes());
        consulta.setStatus(request.getStatus());
        consulta.setMedico(medico);
        consulta.setEnfermeiro(enfermeiro);
        consulta.setPaciente(paciente);
    }
}