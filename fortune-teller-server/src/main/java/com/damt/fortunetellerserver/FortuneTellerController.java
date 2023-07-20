package com.damt.fortunetellerserver;

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

    public FortuneTellerController(FortuneTellerService fortuneTellerService) {
        this.fortuneTellerService = fortuneTellerService;
    }

    @GetMapping("/future/{name}")
    public ResponseEntity<String> tellFuture(@PathVariable String name) {
        fortuneTellerService.tellingFuture(name);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Your future is being told!");
    }

    @GetMapping("/notify")
    public SseEmitter streamSse() {
        SseEmitter emitter = new SseEmitter();
        SseEmitterManager.addEmitter(emitter);

        // Set a timeout for the SSE connection (optional)
        emitter.onTimeout(emitter::complete);

        // Set a handler for client disconnect (optional)
        emitter.onCompletion(() -> SseEmitterManager.removeEmitter(emitter));

        return emitter;
    }
}
