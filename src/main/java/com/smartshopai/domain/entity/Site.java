package com.smartshopai.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "sites")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Site {

    @Id
    private String id;

    @Field("name")
    @Indexed(unique = true)
    private String name;

    @Field("code")
    @Indexed(unique = true)
    private String code; // Unique site code (e.g., SITE001, SITE002)

    @Field("description")
    private String description;

    @Field("address")
    private Address address;

    @Field("contact_info")
    private ContactInfo contactInfo;

    @Field("settings")
    private SiteSettings settings;

    @Field("status")
    @Builder.Default
    private SiteStatus status = SiteStatus.ACTIVE;

    @Field("created_by")
    private String createdBy; // Super admin ID

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Field("deleted_at")
    private LocalDateTime deletedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        @Field("street")
        private String street;

        @Field("city")
        private String city;

        @Field("state")
        private String state;

        @Field("postal_code")
        private String postalCode;

        @Field("country")
        private String country;

        @Field("coordinates")
        private Coordinates coordinates;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coordinates {
        @Field("latitude")
        private Double latitude;

        @Field("longitude")
        private Double longitude;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactInfo {
        @Field("phone")
        private String phone;

        @Field("email")
        private String email;

        @Field("website")
        private String website;

        @Field("emergency_contact")
        private String emergencyContact;

        @Field("emergency_phone")
        private String emergencyPhone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SiteSettings {
        @Field("max_users")
        @Builder.Default
        private Integer maxUsers = 1000;

        @Field("max_buildings")
        @Builder.Default
        private Integer maxBuildings = 50;

        @Field("max_apartments_per_building")
        @Builder.Default
        private Integer maxApartmentsPerBuilding = 100;

        @Field("payment_reminder_days")
        @Builder.Default
        private Integer paymentReminderDays = 7;

        @Field("auto_payment_enabled")
        @Builder.Default
        private boolean autoPaymentEnabled = false;

        @Field("notification_enabled")
        @Builder.Default
        private boolean notificationEnabled = true;

        @Field("file_upload_enabled")
        @Builder.Default
        private boolean fileUploadEnabled = true;

        @Field("max_file_size_mb")
        @Builder.Default
        private Integer maxFileSizeMB = 10;

        @Field("allowed_file_types")
        @Builder.Default
        private List<String> allowedFileTypes = List.of("jpg", "jpeg", "png", "pdf", "doc", "docx");

        @Field("timezone")
        @Builder.Default
        private String timezone = "Europe/Istanbul";

        @Field("language")
        @Builder.Default
        private String language = "tr";

        @Field("currency")
        @Builder.Default
        private String currency = "TRY";
    }

    public enum SiteStatus {
        ACTIVE, INACTIVE, SUSPENDED, DELETED
    }
}
