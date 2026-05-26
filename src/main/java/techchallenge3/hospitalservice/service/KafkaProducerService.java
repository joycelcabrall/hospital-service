package techchallenge3.hospitalservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPICO = "consultas";

    public void enviarMensagem(String mensagem) {

        kafkaTemplate.send(TOPICO, mensagem);

        System.out.println("Mensagem enviada para Kafka: " + mensagem);
    }
}