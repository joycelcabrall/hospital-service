package techchallenge3.hospitalservice.dto;

import lombok.Data;
import techchallenge3.hospitalservice.enums.StatusConsulta;

import java.time.LocalDateTime;

@Data
public class ConsultaRequest {

    private LocalDateTime dataHora;
    private String especialidade;
    private String observacoes;
    private StatusConsulta status;

    private Long medicoId;
    private Long enfermeiroId;
    private Long pacienteId;
}