package com.damt.fortunetellerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@SpringBootApplication
@EnableAsync
public class FortuneTellerServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FortuneTellerServerApplication.class, args);
    }
}

