package com.damt.fortunetellerserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/teller")
@CrossOrigin(origins = "http://localhost:4200")
public class FortuneTellerController {
    private final FortuneTellerService fortuneTellerService;
    private final Logger logger = LoggerFactory.getLogger(FortuneTellerController.class);

    public FortuneTellerController(FortuneTellerService fortuneTellerService) {
        this.fortuneTellerService = fortuneTellerService;
    }

    @GetMapping("/future/{name}")
    public SimpleResponse tellFuture(@PathVariable String name) {
        fortuneTellerService.tellingFuture(name);
        return new SimpleResponse("Your future is being told!");
    }

    @GetMapping("/notify")
    public SseEmitter streamSse() {
        SseEmitter emitter = new SseEmitter();
        logger.info("Emitter created with timeout {}", emitter.getTimeout());
        SseEmitterManager.addEmitter(emitter);

        // Set a timeout for the SSE connection (optional)
        emitter.onTimeout(() -> {
            logger.info("Emitter timed out");
            emitter.complete();
            SseEmitterManager.removeEmitter(emitter);
        });

        // Set a handler for client disconnect (optional)
        emitter.onCompletion(() -> {
            logger.info("Emitter completed");
            SseEmitterManager.removeEmitter(emitter);
        });

        return emitter;
    }
}
record SimpleResponse (String content) {
}