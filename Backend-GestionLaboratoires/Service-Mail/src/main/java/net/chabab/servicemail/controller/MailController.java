package com.example.mailservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody AnalyseDTO analyseDTO) {
        String emailBody = String.format("New Analysis Created:\nName: %s\nDescription: %s", analyseDTO.getNom(), analyseDTO.getDescription());
        mailService.sendEmail("recipient@example.com", "New Analysis Notification", emailBody);
    }
}
