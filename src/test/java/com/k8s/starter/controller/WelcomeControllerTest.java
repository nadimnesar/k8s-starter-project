package com.k8s.starter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WelcomeControllerTest {

    @LocalServerPort
    int port;

    RestClient rest = RestClient.create();

    @Test
    void health() {
        var body = rest.get()
                .uri("http://localhost:" + port + "/health")
                .retrieve()
                .body(String.class);
        assertThat(body).contains("UP");
    }

    @Test
    void welcomeV1() {
        var response = rest.get()
                .uri("http://localhost:" + port + "/welcome")
                .headers(h -> h.set("X-API-Version", "1.0"))
                .retrieve()
                .toEntity(String.class);
        assertThat(response.getBody()).contains("Welcome to K8s World!");
    }

    @Test
    void welcomeV2() {
        var response = rest.get()
                .uri("http://localhost:" + port + "/welcome")
                .headers(h -> h.set("X-API-Version", "2.0"))
                .retrieve()
                .toEntity(String.class);
        assertThat(response.getBody()).contains("Welcome to K8s World!");
    }
}
