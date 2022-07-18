package com.example.logusletterapi.services.schedulingtasks;

import com.example.logusletterapi.model.User;
import com.example.logusletterapi.repository.IUserRepository;
import com.example.logusletterapi.services.SESService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class ScheduledSendingEmail {

    private final IUserRepository userRepository;

    // Second: 0 Minute: 0 Hour: 9 Day: null Month: null Day of the Week: null
    private static final String CRON = "0 28 20 * * *";
    private static final String TIME_ZONE = "America/Sao_Paulo";

    @Scheduled(cron = CRON, zone = TIME_ZONE)
    public void reportCurrentTime() {

        var logger = LoggerFactory.getLogger(ScheduledSendingEmail.class);
        var users = userRepository.findAll();

        logger.info("E-mail sending routine started");

        for (User user: users) {
            SESService.sendMessage("LogosLetter - " + LocalDate.now(), user.getEmail());
        }

        logger.info("E-mail sending routine finished ({})", LocalDate.now());

    }

}
