package com.smartshopai.service;

import com.smartshopai.domain.dto.AuthDto;


public interface AuthenticationService {


    AuthDto.AuthResponse authenticate(AuthDto.LoginRequest loginRequest);


    AuthDto.AuthResponse authenticateWithPhone(AuthDto.PhoneLoginRequest phoneLoginRequest);


    AuthDto.AuthResponse register(AuthDto.RegisterRequest registerRequest);


    AuthDto.AuthResponse refreshToken(AuthDto.RefreshTokenRequest refreshTokenRequest);


    void changePassword(String userId, AuthDto.ChangePasswordRequest changePasswordRequest);


    void logout(String userId);


    boolean validateToken(String token);
}
