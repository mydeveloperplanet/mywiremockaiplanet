package com.mydeveloperplanet.mywiremockaiplanet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

@Configuration
public class AssistantConfiguration {

    @Bean
    public ChatLanguageModel languageModel(MyProperties myProperties) {
        return OpenAiChatModel.builder()
                .apiKey("dummy")
                .baseUrl(myProperties.lmStudioBaseUrl())
                .modelName("llama-3.2-1b-instruct")
                .build();
    }

    @Bean
    public StreamingChatLanguageModel streamingLanguageModel(MyProperties myProperties) {
        return OpenAiStreamingChatModel.builder()
                .apiKey("dummy")
                .baseUrl(myProperties.lmStudioBaseUrl())
                .modelName("llama-3.2-1b-instruct")
                .build();
    }

}
