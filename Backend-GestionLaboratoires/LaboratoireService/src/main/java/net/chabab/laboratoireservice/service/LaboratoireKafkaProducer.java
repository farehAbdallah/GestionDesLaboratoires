package net.chabab.laboratoireservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LaboratoireKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // Le nom du topic Kafka où envoyer les données
    private static final String TOPIC = "laboratoire-topic";

    public LaboratoireKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Méthode pour envoyer des informations au topic Kafka
    public void sendLaboratoireData(String laboratoireData) {
        kafkaTemplate.send(TOPIC, laboratoireData);
    }
}
