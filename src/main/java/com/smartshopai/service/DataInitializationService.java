package com.smartshopai.service;

import com.smartshopai.domain.entity.User;
import com.smartshopai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializationService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeDefaultUser();
    }

    private void initializeDefaultUser() {
        try {
            if (userRepository.count() == 0) {
                log.info("No users found in database. Creating default admin user...");
                
                User adminUser = User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email("admin@smartshopai.com")
                        .phoneNumber("+905551234567")
                        .password(passwordEncoder.encode("admin123"))
                        .apartmentNumber("A1")
                        .buildingNumber("B1")
                        .floorNumber(1)
                        .siteId("default-site")
                        .siteRole(User.SiteRole.SITE_ADMIN)
                        .roles(Set.of(User.Role.ADMIN))
                        .enabled(true)
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                userRepository.save(adminUser);
                log.info("Default admin user created successfully: {}", adminUser.getEmail());
                log.info("Default credentials - Email: admin@smartshopai.com, Password: admin123");
            } else {
                log.info("Database already contains users. Skipping default user creation.");
            }
        } catch (Exception e) {
            log.error("Failed to create default user: {}", e.getMessage(), e);
        }
    }
}
