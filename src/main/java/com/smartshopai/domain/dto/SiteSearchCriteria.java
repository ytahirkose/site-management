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
public class SiteSearchCriteria {

    private String name;
    private String code;
    private String description;
    private Site.SiteStatus status;

    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String region;

    private String contactPhone;
    private String contactEmail;
    private String emergencyContact;

    private Boolean autoPaymentEnabled;
    private Boolean notificationEnabled;
    private Boolean fileUploadEnabled;
    private String timezone;
    private String language;
    private String currency;

    private Integer minMaxUsers;
    private Integer maxMaxUsers;
    private Integer minMaxBuildings;
    private Integer maxMaxBuildings;
    private Integer minMaxApartments;
    private Integer maxMaxApartments;
    private Long minMaxFileSize;
    private Long maxMaxFileSize;

    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private LocalDateTime updatedAfter;
    private LocalDateTime updatedBefore;

    private String createdBy;
    private String[] createdByUsers;

    private List<String> requiredFeatures;
    private List<String> excludedFeatures;
    private List<String> supportedFileTypes;
    private List<String> supportedLanguages;
    private List<String> supportedCurrencies;

    private Integer minUptimePercentage;
    private Integer maxResponseTime;
    private Integer minConcurrentUsers;
    private String performanceRating;

    private String pricingModel;
    private Boolean freeTierAvailable;
    private String costRange; // LOW, MEDIUM, HIGH, ENTERPRISE

    private Boolean sslEnabled;
    private Boolean backupEnabled;
    private Boolean virusScanEnabled;
    private List<String> securityFeatures;

    private List<String> supportLanguages;
    private Integer maxResponseTimeHours;
    private List<String> supportChannels;
    private Boolean phoneSupportAvailable;
    private Boolean emailSupportAvailable;

    private List<String> complianceStandards;
    private Boolean gdprCompliant;
    private Boolean kvkkCompliant;
    private List<String> certifications;

    private List<String> integrations;
    private Boolean apiAvailable;
    private Boolean webhookSupport;
    private List<String> thirdPartyServices;

    private List<String> userRoles;
    private Boolean mobileAppAvailable;
    private Boolean webAppAvailable;
    private List<String> accessibilityFeatures;

    private String scalabilityLevel; // LOW, MEDIUM, HIGH, ENTERPRISE
    private Boolean horizontalScaling;
    private Boolean verticalScaling;
    private Integer growthProjection;

    private Double minRating;
    private Double maxRating;
    private Integer minReviews;
    private Integer maxReviews;

    private Integer minActiveUsers;
    private Integer maxActiveUsers;
    private Integer minMonthlyPayments;
    private Integer maxMonthlyPayments;
    private Integer minMonthlyIssues;
    private Integer maxMonthlyIssues;

    private String searchOperator; // AND, OR
    private Boolean exactMatch;
    private Boolean caseSensitive;
    private String sortBy; // name, created_at, updated_at, status, city, country
    private String sortOrder; // ASC, DESC
    private Integer page;
    private Integer size;
    private Boolean includeInactive;
    private Boolean includeDeleted;
}
