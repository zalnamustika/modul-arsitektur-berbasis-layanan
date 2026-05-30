package com.zalna.email_service.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailTestController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "service", "email-service",
            "status", "UP",
            "message", "Email Service is running"
        );
    }

    @PostMapping("/test")
    public Map<String, String> testEmail(@RequestBody Map<String, String> request) {
        return Map.of(
            "status", "OK",
            "message", "Test email endpoint received",
            "to", request.getOrDefault("to", "not provided")
        );
    }
}
