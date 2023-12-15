package com.webJava.library.helpers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@EnableScheduling
public class TimeService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TimeService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendTimeUpdates() {
        LocalDateTime currentTime = LocalDateTime.now();
        messagingTemplate.convertAndSend("/topic/current-time", currentTime.toString());
    }
}
