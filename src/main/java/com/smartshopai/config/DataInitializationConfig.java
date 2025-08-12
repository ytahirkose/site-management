package com.smartshopai.config;

import com.smartshopai.domain.entity.User;
import com.smartshopai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializationConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeDefaultAdminUser();
    }

    private void initializeDefaultAdminUser() {
        if (userRepository.count() == 0) {
            log.info("No users found. Creating default admin user...");
            
            User adminUser = User.builder()
                    .firstName("Admin")
                    .lastName("User")
                    .email("admin@smartshopai.com")
                    .phoneNumber("+1234567890")
                    .password(passwordEncoder.encode("admin123"))
                    .siteId("default-site")
                    .siteRole(User.SiteRole.SITE_ADMIN)
                    .roles(Set.of(User.Role.ADMIN))
                    .enabled(true)
                    .build();

            userRepository.save(adminUser);
            log.info("Default admin user created successfully: {}", adminUser.getEmail());
        } else {
            log.info("Users already exist in the database. Skipping default user creation.");
        }
    }
}
