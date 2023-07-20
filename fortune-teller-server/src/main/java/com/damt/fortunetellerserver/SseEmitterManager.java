package com.damt.fortunetellerserver;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SseEmitterManager {
    private static final Logger logger = Logger.getLogger(SseEmitterManager.class.getName());
    private static final List<SseEmitter> emitters = new ArrayList<>();

    public static void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
    }

    public static void removeEmitter(SseEmitter emitter) {
        emitters.remove(emitter);
    }

    public static void sendSseEventToClients(String data) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        if (emitters.size() == 0) {
            logger.warning("No clients connected to send event to!");
        }
        emitters.forEach(emitter -> {
            try {
                if (emitter != null)
                    emitter.send(SseEmitter.event().data(data));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });
        emitters.removeAll(deadEmitters);
    }
}
