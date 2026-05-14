package com.k8s.starter;

import module java.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;

/// Spring Boot 4 entry point.
///
/// `@EnableResilientMethods` activates the built-in retry and concurrency
/// limit support from `spring-core` (no extra dependencies needed).
@SpringBootApplication
@EnableResilientMethods
public class K8sStarterApplication {
    static void main(String[] args) {
        SpringApplication.run(K8sStarterApplication.class, args);
    }
}
