package com.smartshopai.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Field("first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Field("last_name")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Field("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @JsonIgnore
    private String password;

    @Field("apartment_number")
    private String apartmentNumber;

    @Field("building_number")
    private String buildingNumber;

    @Field("floor_number")
    private Integer floorNumber;

    @Field("address")
    private String address;

    @Field("city")
    private String city;

    @Field("postal_code")
    private String postalCode;

    @Field("emergency_contact")
    private String emergencyContact;

    @Field("emergency_phone")
    private String emergencyPhone;

    @Field("profile_picture")
    private String profilePicture;

    @Field("date_of_birth")
    private String dateOfBirth;

    @Field("occupation")
    private String occupation;

    @Field("family_members")
    private Integer familyMembers;

    @Field("vehicle_info")
    private String vehicleInfo;

    @Field("preferences")
    private UserPreferences preferences;

    @Field("site_id")
    @NotBlank
    private String siteId; // Site ID for multi-tenant

    @Field("site_role")
    @Builder.Default
    private SiteRole siteRole = SiteRole.RESIDENT; // Role within the site

    @Builder.Default
    private Set<Role> roles = Set.of(Role.RESIDENT);

    @Builder.Default
    @Setter
    private boolean enabled = true;

    @Builder.Default
    @Setter
    private boolean accountNonExpired = true;

    @Builder.Default
    @Setter
    private boolean accountNonLocked = true;

    @Builder.Default
    @Setter
    private boolean credentialsNonExpired = true;

    @Field("fcm_token")
    private String fcmToken;

    @Field("last_login")
    private LocalDateTime lastLogin;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public enum Role {
        SUPER_ADMIN, ADMIN, RESIDENT
    }

    public enum SiteRole {
        SUPER_ADMIN, SITE_ADMIN, RESIDENT
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPreferences {
        @Field("language")
        private String language;

        @Field("timezone")
        private String timezone;

        @Field("notification_enabled")
        @Builder.Default
        private boolean notificationEnabled = true;

        @Field("email_notifications")
        @Builder.Default
        private boolean emailNotifications = true;

        @Field("sms_notifications")
        @Builder.Default
        private boolean smsNotifications = false;

        @Field("theme")
        @Builder.Default
        private String theme = "light";
    }
}
