package com.smartshopai.service;

import com.smartshopai.domain.dto.UserDto;
import com.smartshopai.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(String id, UserDto userDto);

    Optional<UserDto> getUserById(String id);


    Optional<UserDto> getUserByEmail(String email);


    Page<UserDto> getAllUsers(Pageable pageable);


    List<UserDto> getUsersByRole(User.Role role);


    List<UserDto> getUsersByApartmentNumber(String apartmentNumber);


    List<UserDto> getUsersByBuildingNumber(String buildingNumber);


    void deleteUser(String id);


    void changePassword(String userId, String currentPassword, String newPassword);


    void updateFcmToken(String userId, String fcmToken);


    List<UserDto> getUsersWithFcmToken();


    boolean existsByEmail(String email);


    void toggleUserStatus(String userId, boolean enabled);


    Optional<User> getUserEntityByEmail(String email);
}
