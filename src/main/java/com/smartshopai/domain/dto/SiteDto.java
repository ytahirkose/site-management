package com.smartshopai.domain.dto;

import com.smartshopai.domain.entity.Site;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteDto {

    private String id;
    private String name;
    private String code;
    private String description;
    private Address address;
    private ContactInfo contactInfo;
    private SiteSettings settings;
    private Site.SiteStatus status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private Coordinates coordinates;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coordinates {
        private Double latitude;
        private Double longitude;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactInfo {
        private String phone;
        private String email;
        private String website;
        private String emergencyContact;
        private String emergencyPhone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SiteSettings {
        private Integer maxUsers;
        private Integer maxBuildings;
        private Integer maxApartmentsPerBuilding;
        private Integer paymentReminderDays;
        private boolean autoPaymentEnabled;
        private boolean notificationEnabled;
        private boolean fileUploadEnabled;
        private Integer maxFileSizeMB;
        private List<String> allowedFileTypes;
        private String timezone;
        private String language;
        private String currency;
    }
}
