package net.chabab.messagingservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.chabab.messagingservice.dto.EmailRequest;
import net.chabab.messagingservice.dto.SmsRequest;
import net.chabab.messagingservice.service.EmailService;
import net.chabab.messagingservice.service.SmsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationConsumer {

    private final SmsService smsService;

    public NotificationConsumer(SmsService smsService) {
        this.smsService = smsService;
    }

    @KafkaListener(topics = "email-topic", groupId = "messagerie-group")
    public void handleEmail(String jsonPayload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> variables = new HashMap<>();
        EmailRequest emailRequest = objectMapper.readValue(jsonPayload, EmailRequest.class); // Convert JSON string to object
        EmailService emailService = new EmailService("zinetsu00@gmail.com","iigx xdqv qtzs hwid");
        variables.put("name", emailRequest.getRecipient());
        variables.put("email", emailRequest.getRecipient());
        variables.put("subscription", "Premium");
        emailService.sendEmail(emailRequest.getRecipient(), emailRequest.getSubject(), "welcome-email", variables);
        System.out.println("Processed email request: " + emailRequest);
    }

    @KafkaListener(topics = "sms-topic", groupId = "messagerie-group")
    public void handleSms(String jsonPayload) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        SmsRequest smsRequest = objectMapper.readValue(jsonPayload, SmsRequest.class); // Convert JSON string to object
        smsService.sendSms(smsRequest);
        System.out.println("Processed sms request: " + smsRequest);
    }
}

