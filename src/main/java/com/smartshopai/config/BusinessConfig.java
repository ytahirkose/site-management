package com.smartshopai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "app.business")
@Data
public class BusinessConfig {

    private Payment payment = new Payment();
    private Notification notification = new Notification();
    private File file = new File();

    @Data
    public static class Payment {
        private BigDecimal lateFeeRate = new BigDecimal("0.05");
        private BigDecimal maxLateFeePercentage = new BigDecimal("0.50");
        private Integer reminderIntervalDays = 7;
    }

    @Data
    public static class Notification {
        private Integer batchSize = 100;
        private Integer retryDelaySeconds = 300;
    }

    @Data
    public static class File {
        private boolean virusScanEnabled = false;
        private boolean compressionEnabled = true;
        private boolean thumbnailEnabled = true;
    }
}
