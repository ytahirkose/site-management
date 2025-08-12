package com.smartshopai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class SiteManagementApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
        assertNotNull(applicationContext);
    }

    @Test
    void applicationStarts() {
        // Test that the application starts without errors
        assertNotNull(applicationContext);
    }

    @Test
    void beansAreCreated() {
        // Test that all required beans are created
        assertNotNull(applicationContext.getBean("paymentService"));
        assertNotNull(applicationContext.getBean("userService"));
        assertNotNull(applicationContext.getBean("announcementService"));
        assertNotNull(applicationContext.getBean("issueService"));
        assertNotNull(applicationContext.getBean("fileService"));
        assertNotNull(applicationContext.getBean("notificationService"));
    }

    @Test
    void configurationPropertiesLoaded() {
        // Test that configuration properties are loaded
        assertNotNull(applicationContext.getBean("businessConfig"));
    }

    @Test
    void cacheManagerConfigured() {
        // Test that cache manager is configured
        assertNotNull(applicationContext.getBean("cacheManager"));
    }

    @Test
    void taskExecutorConfigured() {
        // Test that task executor is configured
        assertNotNull(applicationContext.getBean("taskExecutor"));
    }
}
