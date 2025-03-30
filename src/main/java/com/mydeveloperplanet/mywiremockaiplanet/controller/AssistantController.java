package com.mydeveloperplanet.mywiremockaiplanet.controller;

import com.mydeveloperplanet.mywiremockaiplanet.service.Assistant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
class AssistantController {

    Assistant assistant;

    public AssistantController(Assistant assistant) {
        this.assistant = assistant;
    }

    @GetMapping("/chat")
    public String chat(String message) {
        return assistant.chat(message);
    }

    @GetMapping("/stream")
    public Flux<String> stream(String message) {
        return assistant.stream(message);
    }

}