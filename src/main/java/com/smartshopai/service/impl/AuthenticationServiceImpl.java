package com.smartshopai.service.impl;

import com.smartshopai.domain.dto.AuthDto;
import com.smartshopai.domain.dto.UserDto;
import com.smartshopai.domain.entity.User;
import com.smartshopai.domain.mapper.UserMapper;
import com.smartshopai.exception.AuthenticationException;
import com.smartshopai.exception.UserAlreadyExistsException;
import com.smartshopai.repository.UserRepository;
import com.smartshopai.security.JwtService;
import com.smartshopai.service.AuthenticationService;
import com.smartshopai.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthDto.AuthResponse authenticate(AuthDto.LoginRequest loginRequest) {
        log.info("Authenticating user with email: {}", loginRequest.getEmail());

        try {
            // Önce kullanıcıyı veritabanından bulalım
            log.info("Looking up user in database with email: {}", loginRequest.getEmail());
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElse(null);
            
            if (user == null) {
                log.error("User not found in database with email: {}", loginRequest.getEmail());
                throw new AuthenticationException("Invalid email or password");
            }
            
            log.info("User found in database: ID={}, Email={}, Enabled={}, PasswordHash={}", 
                    user.getId(), user.getEmail(), user.isEnabled(), 
                    user.getPassword() != null ? user.getPassword().substring(0, Math.min(20, user.getPassword().length())) + "..." : "null");
            
            // Şifre karşılaştırmasını manuel olarak yapalım
            log.info("Comparing passwords for user: {}", loginRequest.getEmail());
            boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            log.info("Password match result: {}", passwordMatches);
            
            if (!passwordMatches) {
                log.error("Password does not match for user: {}", loginRequest.getEmail());
                throw new AuthenticationException("Invalid email or password");
            }
            
            if (!user.isEnabled()) {
                log.error("User account is disabled: {}", loginRequest.getEmail());
                throw new AuthenticationException("User account is disabled");
            }

            // Spring Security authentication'ı da yapalım
            log.info("Performing Spring Security authentication for user: {}", loginRequest.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.info("Spring Security authentication successful for user: {}", loginRequest.getEmail());

            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            log.debug("Generating JWT token");
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            log.debug("JWT tokens generated successfully");

            log.info("User authenticated successfully: {}", user.getEmail());

            return AuthDto.AuthResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtService.getJwtExpiration())
                    .user(userMapper.toDto(user))
                    .build();

        } catch (org.springframework.security.core.AuthenticationException e) {
            log.error("Authentication failed for user: {}", loginRequest.getEmail(), e);
            throw new AuthenticationException("Invalid email or password");
        } catch (Exception e) {
            log.error("Unexpected error during authentication for user: {}", loginRequest.getEmail(), e);
            throw new AuthenticationException("Authentication failed: " + e.getMessage());
        }
    }

    @Override
    public AuthDto.AuthResponse authenticateWithPhone(AuthDto.PhoneLoginRequest phoneLoginRequest) {
        log.info("Authenticating user with phone: {}", phoneLoginRequest.getPhoneNumber());

        User user = userRepository.findByPhoneNumber(phoneLoginRequest.getPhoneNumber())
                .orElseThrow(() -> new AuthenticationException("User not found with phone number"));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(), // Use email for authentication
                            phoneLoginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            log.info("User authenticated successfully with phone: {}", user.getPhoneNumber());

            return AuthDto.AuthResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtService.getJwtExpiration())
                    .user(userMapper.toDto(user))
                    .build();

        } catch (org.springframework.security.core.AuthenticationException e) {
            log.error("Authentication failed for user with phone: {}", phoneLoginRequest.getPhoneNumber());
            throw new AuthenticationException("Invalid phone number or password");
        } catch (Exception e) {
            log.error("Unexpected error during phone authentication for user: {}", phoneLoginRequest.getPhoneNumber(), e);
            throw new AuthenticationException("Phone authentication failed: " + e.getMessage());
        }
    }

    @Override
    public AuthDto.AuthResponse register(AuthDto.RegisterRequest registerRequest) {
        log.info("Registering new user with email: {}", registerRequest.getEmail());

        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + registerRequest.getEmail() + " already exists");
        }

        UserDto userDto = UserDto.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(registerRequest.getPassword())
                .apartmentNumber(registerRequest.getApartmentNumber())
                .buildingNumber(registerRequest.getBuildingNumber())
                .floorNumber(registerRequest.getFloorNumber())
                .roles(Set.of(User.Role.RESIDENT))
                .enabled(true)
                .build();

        UserDto createdUser = userService.createUser(userDto);
        User user = userRepository.findByEmail(createdUser.getEmail())
                .orElseThrow(() -> new RuntimeException("Failed to create user"));

        UserDetails userDetails = user;
        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        log.info("User registered successfully: {}", user.getEmail());

        return AuthDto.AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getJwtExpiration())
                .user(createdUser)
                .build();
    }

    @Override
    public AuthDto.AuthResponse refreshToken(AuthDto.RefreshTokenRequest refreshTokenRequest) {
        log.debug("Refreshing JWT token");

        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();
            String userEmail = jwtService.extractUsername(refreshToken);

            if (userEmail != null) {
                User user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new AuthenticationException("User not found"));

                if (jwtService.isTokenValid(refreshToken, user)) {
                    String newToken = jwtService.generateToken(user);
                    String newRefreshToken = jwtService.generateRefreshToken(user);

                    log.debug("Token refreshed successfully for user: {}", userEmail);

                    return AuthDto.AuthResponse.builder()
                            .token(newToken)
                            .refreshToken(newRefreshToken)
                            .tokenType("Bearer")
                            .expiresIn(jwtService.getJwtExpiration())
                            .user(userMapper.toDto(user))
                            .build();
                }
            }

            throw new AuthenticationException("Invalid refresh token");

        } catch (Exception e) {
            log.error("Token refresh failed: {}", e.getMessage());
            throw new AuthenticationException("Failed to refresh token");
        }
    }

    @Override
    public void changePassword(String userId, AuthDto.ChangePasswordRequest changePasswordRequest) {
        log.info("Changing password for user with ID: {}", userId);
        userService.changePassword(userId, changePasswordRequest.getCurrentPassword(), changePasswordRequest.getNewPassword());
    }

    @Override
    public void logout(String userId) {
        log.info("Logging out user with ID: {}", userId);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            String userEmail = jwtService.extractUsername(token);
            if (userEmail != null) {
                User user = userRepository.findByEmail(userEmail).orElse(null);
                return user != null && jwtService.isTokenValid(token, user);
            }
            return false;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
}
