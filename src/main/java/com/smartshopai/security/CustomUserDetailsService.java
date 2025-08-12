package com.smartshopai.security;

import com.smartshopai.domain.entity.User;
import com.smartshopai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading user by email: {}", email);
        
        try {
            // MongoDB'den kullanıcıyı çekmeye çalış
            log.info("Attempting to find user in database with email: {}", email);
            User user = userRepository.findByEmail(email)
                    .orElse(null);
            
            if (user == null) {
                log.error("User not found in database with email: {}", email);
                throw new UsernameNotFoundException("User not found with email: " + email);
            }

            log.info("User found in database: ID={}, Email={}, Enabled={}", user.getId(), user.getEmail(), user.isEnabled());
            
            if (!user.isEnabled()) {
                log.warn("User account is disabled: {}", email);
                throw new UsernameNotFoundException("User account is disabled: " + email);
            }

            log.info("User loaded successfully: {}", email);
            return user;
            
        } catch (Exception e) {
            log.error("Error loading user by email: {}", email, e);
            throw new UsernameNotFoundException("Error loading user: " + e.getMessage(), e);
        }
    }
}
