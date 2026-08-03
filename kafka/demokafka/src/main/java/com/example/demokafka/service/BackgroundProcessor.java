package com.example.demokafka.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BackgroundProcessor {
    @Scheduled(fixedRate = 600)
    public void cleanOldFiles() {
        System.out.println("Running file cleanup...");
        // logic here
    }
}
