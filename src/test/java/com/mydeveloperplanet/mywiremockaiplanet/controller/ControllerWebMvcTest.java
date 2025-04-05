package com.mydeveloperplanet.mywiremockaiplanet.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mydeveloperplanet.mywiremockaiplanet.service.Assistant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AssistantController.class)
class ControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Assistant assistant;

    @Test
    void testChat() throws Exception {
        when(assistant.chat("Tell me a joke")).thenReturn("This is a joke");

        mockMvc.perform(MockMvcRequestBuilders.get("/chat")
                        .param("message", "Tell me a joke")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("This is a joke"));
    }

}
