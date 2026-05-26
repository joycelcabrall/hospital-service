package techchallenge3.hospitalservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "consultas", groupId = "hospital-group")
    public void ouvirMensagem(String mensagem) {

        System.out.println("Mensagem recebida do Kafka: " + mensagem);
    }
}