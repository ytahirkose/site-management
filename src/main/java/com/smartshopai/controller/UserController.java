package com.smartshopai.controller;

import com.smartshopai.domain.dto.UserDto;
import com.smartshopai.domain.entity.User;
import com.smartshopai.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Users", description = "User management APIs")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Get current user profile", description = "Get profile of currently authenticated user")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Getting profile for user: {}", userDetails.getUsername());
        UserDto user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    @Operation(summary = "Update current user profile", description = "Update profile of currently authenticated user")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateCurrentUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserDto userDto) {
        log.info("Updating profile for user: {}", userDetails.getUsername());
        UserDto currentUser = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserDto updatedUser = userService.updateUser(currentUser.getId(), userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Get user details by ID (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        log.debug("Getting user by ID: {}", id);
        UserDto user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users with pagination (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        log.debug("Getting all users with pagination");
        Page<UserDto> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-role/{role}")
    @Operation(summary = "Get users by role", description = "Get users by specific role (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable String role) {
        log.debug("Getting users by role: {}", role);
        User.Role userRole = User.Role.valueOf(role.toUpperCase());
        List<UserDto> users = userService.getUsersByRole(userRole);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-apartment/{apartmentNumber}")
    @Operation(summary = "Get users by apartment", description = "Get users by apartment number (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByApartment(@PathVariable String apartmentNumber) {
        log.debug("Getting users by apartment: {}", apartmentNumber);
        List<UserDto> users = userService.getUsersByApartmentNumber(apartmentNumber);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-building/{buildingNumber}")
    @Operation(summary = "Get users by building", description = "Get users by building number (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByBuilding(@PathVariable String buildingNumber) {
        log.debug("Getting users by building: {}", buildingNumber);
        List<UserDto> users = userService.getUsersByBuildingNumber(buildingNumber);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Create a new user (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Creating new user with email: {}", userDto.getEmail());
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update existing user (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @Valid @RequestBody UserDto userDto) {
        log.info("Updating user with ID: {}", id);
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete user by ID (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/toggle-status")
    @Operation(summary = "Toggle user status", description = "Enable/disable user account (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> toggleUserStatus(@PathVariable String id, @RequestParam boolean enabled) {
        log.info("Toggling user status to {} for user ID: {}", enabled, id);
        userService.toggleUserStatus(id, enabled);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fcm-token")
    @Operation(summary = "Update FCM token", description = "Update FCM token for push notifications")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateFcmToken(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String fcmToken) {
        log.debug("Updating FCM token for user: {}", userDetails.getUsername());
        UserDto currentUser = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        userService.updateFcmToken(currentUser.getId(), fcmToken);
        return ResponseEntity.ok().build();
    }
}
