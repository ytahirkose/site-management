package com.smartshopai.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartshopai.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phoneNumber;

    @JsonIgnore
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String apartmentNumber;
    private String buildingNumber;
    private Integer floorNumber;
    private String address;
    private String city;
    private String postalCode;
    private String emergencyContact;
    private String emergencyPhone;
    private String profilePicture;
    private String dateOfBirth;
    private String occupation;
    private Integer familyMembers;
    private String vehicleInfo;
    private User.UserPreferences preferences;
    private Set<User.Role> roles;
    private boolean enabled;
    private String fcmToken;
    private LocalDateTime lastLogin;
    private String siteId; // Site ID for multi-tenant
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String fullName;
    private boolean isAdmin;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
