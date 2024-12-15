package net.chabab.messagingservice.service;


import net.chabab.messagingservice.dto.SmsRequest;

public interface SmsService {

    void sendSms(SmsRequest smsRequest);
}
