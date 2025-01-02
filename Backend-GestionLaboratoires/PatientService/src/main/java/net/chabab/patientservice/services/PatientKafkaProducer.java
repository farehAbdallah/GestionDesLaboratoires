package net.chabab.patientservice.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatientKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "patient-topic";  // Le topic Kafka pour patient

    public PatientKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Méthode pour envoyer les données via Kafka
    public void sendPatientData(String patientData) {
        kafkaTemplate.send(TOPIC, patientData);  // Envoi du message au topic Kafka
    }
}
