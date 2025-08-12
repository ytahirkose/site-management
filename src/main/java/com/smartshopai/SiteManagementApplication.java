package com.smartshopai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoAuditing
@EnableAsync
@EnableScheduling
@EnableCaching
public class SiteManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiteManagementApplication.class, args);
    }
}
