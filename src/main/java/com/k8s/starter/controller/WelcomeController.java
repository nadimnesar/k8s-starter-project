package com.k8s.starter.controller;

import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/// Versioned welcome API.
///
/// Uses **Spring Boot 4 API versioning** via the `version` attribute on
/// `@GetMapping`. The client selects a version with the `X-API-Version` header.
///
/// Both endpoints are decorated with `@Retryable` from Spring Framework 7's
/// built-in resilience support (`spring-core`, no spring-retry dependency).
@RestController
public class WelcomeController {

    /// Returns the v1 welcome message.
    @GetMapping(value = "/welcome", version = "1.0")
    @Retryable(maxRetries = 2)
    public Message welcomeV1() {
        return new Message("Welcome to K8s World!", 1);
    }

    /// Returns the v2 welcome message.
    @GetMapping(value = "/welcome", version = "2.0")
    @Retryable(maxRetries = 4)
    public Message welcomeV2() {
        return new Message("Welcome to K8s World!", 2);
    }

    /// A **Java 25 `record`** (JEP 395) serving as an immutable data carrier.
    ///
    /// @param text       the welcome message text
    /// @param apiVersion the API version number
    public record Message(String text, int apiVersion) {
    }
}
