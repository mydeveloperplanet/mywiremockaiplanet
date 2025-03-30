package com.mydeveloperplanet.mywiremockaiplanet.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface Assistant {

    @SystemMessage("You are a polite assistant")
    String chat(String userMessage);

    @SystemMessage("You are a polite assistant")
    Flux<String> stream(String userMessage);

}