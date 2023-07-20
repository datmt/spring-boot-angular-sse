package com.damt.fortunetellerserver;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.logging.Logger;

@Service
public class FortuneTellerService {
    private final Logger logger = Logger.getLogger(FortuneTellerService.class.getName());
    @Async
    public void tellingFuture(String subscriberId, String name) {
        Random random = new Random();
        try {
            logger.info("Processing future for " + name);
            Thread.sleep(random.nextInt(5_000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Finished processing future for " + name);
        String processedData =String.format("For %s percent certainty, %s will have a %s future!", random.nextInt(100), name, random.nextBoolean() ? "bright" : "dark");
        SseEmitterManager.sendSseEventToClients(subscriberId, processedData);
    }
}
