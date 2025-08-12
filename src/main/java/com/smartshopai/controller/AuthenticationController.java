package com.smartshopai.controller;

import com.smartshopai.domain.dto.AuthDto;
import com.smartshopai.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "User login with email", description = "Authenticate user with email and password and return JWT tokens")
    public ResponseEntity<AuthDto.AuthResponse> login(@Valid @RequestBody AuthDto.LoginRequest loginRequest) {
        log.info("Login attempt for user: {}", loginRequest.getEmail());
        AuthDto.AuthResponse response = authenticationService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/phone")
    @Operation(summary = "User login with phone", description = "Authenticate user with phone number and password and return JWT tokens")
    public ResponseEntity<AuthDto.AuthResponse> loginWithPhone(@Valid @RequestBody AuthDto.PhoneLoginRequest phoneLoginRequest) {
        log.info("Phone login attempt for user: {}", phoneLoginRequest.getPhoneNumber());
        AuthDto.AuthResponse response = authenticationService.authenticateWithPhone(phoneLoginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register new user and return JWT tokens")
    public ResponseEntity<AuthDto.AuthResponse> register(@Valid @RequestBody AuthDto.RegisterRequest registerRequest) {
        log.info("Registration attempt for user: {}", registerRequest.getEmail());
        AuthDto.AuthResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Refresh JWT token using refresh token")
    public ResponseEntity<AuthDto.AuthResponse> refreshToken(@Valid @RequestBody AuthDto.RefreshTokenRequest refreshTokenRequest) {
        log.debug("Token refresh request");
        AuthDto.AuthResponse response = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change password", description = "Change user password")
    public ResponseEntity<Void> changePassword(
            @RequestParam String userId,
            @Valid @RequestBody AuthDto.ChangePasswordRequest changePasswordRequest) {
        log.info("Password change request for user: {}", userId);
        authenticationService.changePassword(userId, changePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logout user and invalidate token")
    public ResponseEntity<Void> logout(@RequestParam String userId) {
        log.info("Logout request for user: {}", userId);
        authenticationService.logout(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate token", description = "Validate JWT token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        log.debug("Token validation request");
        boolean isValid = authenticationService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
}
