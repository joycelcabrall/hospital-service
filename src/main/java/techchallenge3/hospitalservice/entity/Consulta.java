package techchallenge3.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import techchallenge3.hospitalservice.enums.StatusConsulta;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private String especialidade;

    private String observacoes;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Usuario medico;

    @ManyToOne
    @JoinColumn(name = "enfermeiro_id")
    private Usuario enfermeiro;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Usuario paciente;
}