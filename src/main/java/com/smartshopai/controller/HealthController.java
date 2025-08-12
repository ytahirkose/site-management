package com.smartshopai.controller;

import com.smartshopai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Site Management API");
        health.put("version", "1.0.0");
        return ResponseEntity.ok(health);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/db-test")
    public ResponseEntity<Map<String, Object>> testDatabase() {
        Map<String, Object> result = new HashMap<>();
        try {
            // MongoDB bağlantısını test et
            long userCount = userRepository.count();
            result.put("status", "SUCCESS");
            result.put("message", "Database connection successful");
            result.put("userCount", userCount);
            result.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Database connection failed: " + e.getMessage());
            result.put("error", e.getClass().getSimpleName());
            result.put("timestamp", LocalDateTime.now());
            return ResponseEntity.status(500).body(result);
        }
    }

    @GetMapping("/env-test")
    public ResponseEntity<Map<String, Object>> testEnvironmentVariables() {
        Map<String, Object> result = new HashMap<>();
        
        // Environment variables'ları kontrol et
        String mongoUri = System.getenv("MONGODB_URI");
        String jwtSecret = System.getenv("JWT_SECRET");
        String mongoDatabase = System.getenv("MONGODB_DATABASE");
        
        result.put("mongoUri", mongoUri != null ? mongoUri.substring(0, Math.min(50, mongoUri.length())) + "..." : "NOT_SET");
        result.put("jwtSecret", jwtSecret != null ? jwtSecret.substring(0, Math.min(20, jwtSecret.length())) + "..." : "NOT_SET");
        result.put("mongoDatabase", mongoDatabase != null ? mongoDatabase : "NOT_SET");
        result.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(result);
    }
}
