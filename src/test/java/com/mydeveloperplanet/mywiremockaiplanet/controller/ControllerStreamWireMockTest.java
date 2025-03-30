package com.mydeveloperplanet.mywiremockaiplanet.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import com.github.tomakehurst.wiremock.client.WireMock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.wiremock.spring.EnableWireMock;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
@TestPropertySource(properties = {
        "my.properties.lm-studio-base-url=http://localhost:${wiremock.server.port}/v1"
})
class ControllerStreamWireMockTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testStreamFlux() {

        stubFor(post(WireMock.urlPathEqualTo("/v1/chat/completions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/event-stream")
                        .withBody("""
                                data: {"id":"chatcmpl-tnh9pc0j6m91mm9duk4c4x","object":"chat.completion.chunk","created":1743325543,"model":"llama-3.2-1b-instruct","system_fingerprint":"llama-3.2-1b-instruct","choices":[{"index":0,"delta":{"role":"assistant","content":"Here"},"logprobs":null,"finish_reason":null}]}

                                data: {"id":"chatcmpl-tnh9pc0j6m91mm9duk4c4x","object":"chat.completion.chunk","created":1743325543,"model":"llama-3.2-1b-instruct","system_fingerprint":"llama-3.2-1b-instruct","choices":[{"index":0,"delta":{"role":"assistant","content":"'s"},"logprobs":null,"finish_reason":null}]}

                                data: {"id":"chatcmpl-tnh9pc0j6m91mm9duk4c4x","object":"chat.completion.chunk","created":1743325543,"model":"llama-3.2-1b-instruct","system_fingerprint":"llama-3.2-1b-instruct","choices":[{"index":0,"delta":{"role":"assistant","content":" one"},"logprobs":null,"finish_reason":null}]}
                                
                                data: {"id":"chatcmpl-tnh9pc0j6m91mm9duk4c4x","object":"chat.completion.chunk","created":1743325543,"model":"llama-3.2-1b-instruct","system_fingerprint":"llama-3.2-1b-instruct","choices":[{"index":0,"delta":{},"logprobs":null,"finish_reason":"stop"}]}
                                
                                data: [DONE]""")));


        // Use WebClient to make a request to /stream endpoint
        Flux<String> response = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stream").queryParam("message", "Tell me a joke").build())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();

        // Verify streamed data using StepVerifier
        StepVerifier.create(response)
                .expectNext("Here")
                .expectNext("'s")
                .expectNext("one") // spaces are stripped
                .verifyComplete();

    }

}
