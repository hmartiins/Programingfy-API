package com.example.logusletterapi.services.schedulingtasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledSendingEmail {

    // Second: 0 Minute: 0 Hour: 9 Day: null Month: null Day of the Week: null
    private static final String CRON = "0 0 9 * * *";
    private static final String TIME_ZONE = "America/Sao_Paulo";

    @Scheduled(cron = CRON, zone = TIME_ZONE)
    public void reportCurrentTime() {
        System.out.println("Executou o Scheduled");
    }

}
