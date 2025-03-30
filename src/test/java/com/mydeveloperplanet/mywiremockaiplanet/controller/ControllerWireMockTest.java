package com.mydeveloperplanet.mywiremockaiplanet.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
@TestPropertySource(properties = {
        "my.properties.lm-studio-base-url=http://localhost:${wiremock.server.port}/v1"
})
@AutoConfigureMockMvc
class ControllerWireMockTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testChat() throws Exception {

        stubFor(post(urlEqualTo("/v1/chat/completions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(BODY)));

        mockMvc.perform(MockMvcRequestBuilders.get("/chat")
                        .param("message", "Tell me a joke")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("This works!"));

    }

    @Test
    void testChatCommonResponse() throws Exception {

        stubFor(post(urlEqualTo("/v1/chat/completions"))
                .willReturn(okJson(BODY)));

        mockMvc.perform(MockMvcRequestBuilders.get("/chat")
                        .param("message", "Tell me a joke")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("This works!"));

    }

    @Test
    void testChatFromFile() throws Exception {

        stubFor(post(urlEqualTo("/v1/chat/completions"))
                .willReturn(aResponse().withBodyFile("stubs/jokestub.json")));

        mockMvc.perform(MockMvcRequestBuilders.get("/chat")
                        .param("message", "Tell me a joke")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("This works!"));

    }

    @Test
    void testChatWithRequestBody() throws Exception {

        stubFor(post(urlEqualTo("/v1/chat/completions"))
                .withRequestBody(matchingJsonPath("$.messages[?(@.content == 'Tell me a joke')]"))
                .willReturn(aResponse().withBodyFile("stubs/jokestub.json")));

        stubFor(post(urlEqualTo("/v1/chat/completions"))
                .withRequestBody(matchingJsonPath("$.messages[?(@.content == 'Tell me another joke')]"))
                .willReturn(aResponse().withBodyFile("stubs/anotherjokestub.json")));

        mockMvc.perform(MockMvcRequestBuilders.get("/chat")
                        .param("message", "Tell me a joke")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("This works!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/chat")
                        .param("message", "Tell me another joke")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("This works also!"));

    }

    private static final String BODY = """
            {
              "id": "chatcmpl-p1731vusmgq3oh0xqnkay4",
              "object": "chat.completion",
              "created": 1743244125,
              "model": "llama-3.2-1b-instruct",
              "choices": [
                {
                  "index": 0,
                  "message": {
                    "role": "assistant",
                    "content": "This works!"
                  },
                  "logprobs": null,
                  "finish_reason": "stop"
                }
              ],
              "usage": {
                "prompt_tokens": 24,
                "completion_tokens": 36,
                "total_tokens": 60
              },
              "system_fingerprint": "llama-3.2-1b-instruct"
            }
            """;

}
