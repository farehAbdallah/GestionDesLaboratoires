package net.chabab.gestionanalyse.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnalyseKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // Nom du topic où envoyer les messages
    private static final String TOPIC = "analyse-topic";

    public AnalyseKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Méthode pour envoyer les informations sur l'analyse
    public void sendAnalyseData(String analyseData) {
        kafkaTemplate.send(TOPIC, analyseData);
    }
}
