package com.smartshopai.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.Base64;

@Configuration
@Slf4j
public class JwtConfig {

    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;

    @Value("${spring.security.jwt.expiration}")
    private long jwtExpiration;

    @PostConstruct
    public void validateJwtConfiguration() {
        try {
            // Validate JWT secret
            if (jwtSecret == null || jwtSecret.trim().isEmpty()) {
                log.error("JWT secret is not configured properly");
                throw new IllegalStateException("JWT secret must be configured");
            }

            // Validate JWT secret length (should be at least 256 bits = 32 bytes)
            byte[] secretBytes = Base64.getDecoder().decode(jwtSecret);
            if (secretBytes.length < 32) {
                log.warn("JWT secret is shorter than recommended 256 bits. Current length: {} bits", secretBytes.length * 8);
            }

            // Validate JWT expiration
            if (jwtExpiration <= 0) {
                log.error("JWT expiration must be positive");
                throw new IllegalStateException("JWT expiration must be positive");
            }

            log.info("JWT configuration validated successfully. Secret length: {} bits, Expiration: {} ms", 
                    secretBytes.length * 8, jwtExpiration);

        } catch (IllegalArgumentException e) {
            log.error("Invalid JWT secret format. Secret must be Base64 encoded: {}", e.getMessage());
            throw new IllegalStateException("Invalid JWT secret format", e);
        } catch (Exception e) {
            log.error("Error validating JWT configuration: {}", e.getMessage());
            throw new IllegalStateException("JWT configuration validation failed", e);
        }
    }
}
