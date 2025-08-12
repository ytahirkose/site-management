package com.smartshopai.service.impl;

import com.smartshopai.domain.dto.UserDto;
import com.smartshopai.domain.entity.User;
import com.smartshopai.domain.mapper.UserMapper;
import com.smartshopai.exception.ResourceNotFoundException;
import com.smartshopai.exception.UserAlreadyExistsException;
import com.smartshopai.repository.UserRepository;
import com.smartshopai.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Creating new user with email: {}", userDto.getEmail());

        if (existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + userDto.getEmail() + " already exists");
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());

        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        log.info("Updating user with ID: {}", id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        userMapper.updateEntityFromDto(userDto, existingUser);
        existingUser.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(existingUser);
        log.info("User updated successfully with ID: {}", updatedUser.getId());

        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(String id) {
        log.debug("Getting user by ID: {}", id);
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        log.debug("Getting user by email: {}", email);
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        log.debug("Getting all users with pagination");
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsersByRole(User.Role role) {
        log.debug("Getting users by role: {}", role);
        return userMapper.toDtoList(userRepository.findByRolesContaining(role));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsersByApartmentNumber(String apartmentNumber) {
        log.debug("Getting users by apartment number: {}", apartmentNumber);
        return userMapper.toDtoList(userRepository.findByApartmentNumber(apartmentNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsersByBuildingNumber(String buildingNumber) {
        log.debug("Getting users by building number: {}", buildingNumber);
        return userMapper.toDtoList(userRepository.findByBuildingNumber(buildingNumber));
    }

    @Override
    public void deleteUser(String id) {
        log.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

    @Override
    public void changePassword(String userId, String currentPassword, String newPassword) {
        log.info("Changing password for user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        log.info("Password changed successfully for user with ID: {}", userId);
    }

    @Override
    public void updateFcmToken(String userId, String fcmToken) {
        log.debug("Updating FCM token for user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setFcmToken(fcmToken);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        log.debug("FCM token updated for user with ID: {}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsersWithFcmToken() {
        log.debug("Getting users with FCM tokens");
        return userMapper.toDtoList(userRepository.findUsersWithFcmToken());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void toggleUserStatus(String userId, boolean enabled) {
        log.info("Toggling user status to {} for user with ID: {}", enabled, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setEnabled(enabled);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        log.info("User status toggled to {} for user with ID: {}", enabled, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
