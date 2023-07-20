package com.damt.fortunetellerserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/teller")
@CrossOrigin(origins = "http://localhost:4200")
public class FortuneTellerController {
    private final FortuneTellerService fortuneTellerService;
    private final Logger logger = LoggerFactory.getLogger(FortuneTellerController.class);

    public FortuneTellerController(FortuneTellerService fortuneTellerService) {
        this.fortuneTellerService = fortuneTellerService;
    }

    @GetMapping("/future/{name}/{subscriberId}")
    public SimpleResponse tellFuture(@PathVariable String name, @PathVariable String subscriberId) {
        fortuneTellerService.tellingFuture(subscriberId, name);
        return new SimpleResponse("Your future is being told!");
    }

    @GetMapping("/subscribe/{subscriberId}")
    public SseEmitter streamSse(@PathVariable String subscriberId) {
        SseEmitter emitter = new SseEmitter();
        logger.info("Emitter created with timeout {} for subscriberId {}", emitter.getTimeout(), subscriberId);
        SseEmitterManager.addEmitter(subscriberId, emitter);

        // Set a timeout for the SSE connection (optional)
        emitter.onTimeout(() -> {
            logger.info("Emitter timed out");
            emitter.complete();
            SseEmitterManager.removeEmitter(subscriberId);
        });

        // Set a handler for client disconnect (optional)
        emitter.onCompletion(() -> {
            logger.info("Emitter completed");
            SseEmitterManager.removeEmitter(subscriberId);
        });

        return emitter;
    }
}

record SimpleResponse(String content) {
}