package com.smartshopai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteRecommendationCriteria {

    private String preferredCity;
    private String preferredCountry;
    private String preferredRegion;
    private String[] excludedCities;
    private String[] excludedCountries;

    private Integer minUsers;
    private Integer maxUsers;
    private Integer minBuildings;
    private Integer maxBuildings;
    private Integer minApartments;
    private Integer maxApartments;
    private Long minStorage;
    private Long maxStorage;

    private BigDecimal maxMonthlyCost;
    private BigDecimal maxYearlyCost;
    private String preferredPricingModel; // FREE, PAY_PER_USE, SUBSCRIPTION, ENTERPRISE
    private boolean includeFreeTier;

    private List<String> requiredFeatures;
    private List<String> preferredFeatures;
    private List<String> excludedFeatures;
    private boolean autoPaymentRequired;
    private boolean notificationRequired;
    private boolean fileUploadRequired;
    private boolean virusScanRequired;

    private String[] requiredLanguages;
    private String[] preferredLanguages;
    private String[] requiredCurrencies;
    private String preferredTimezone;

    private Integer minUptimePercentage;
    private Integer maxResponseTime;
    private Integer minConcurrentUsers;
    private String performanceRequirement; // BASIC, STANDARD, PREMIUM, ENTERPRISE

    private boolean sslRequired;
    private boolean backupRequired;
    private boolean auditLogRequired;
    private String[] requiredSecurityFeatures;

    private String[] requiredSupportLanguages;
    private Integer maxResponseTimeHours;
    private boolean phoneSupportRequired;
    private boolean emailSupportRequired;
    private String[] requiredSupportChannels;

    private String[] requiredFileTypes;
    private Integer minMaxFileSize;
    private boolean thumbnailGenerationRequired;
    private boolean previewGenerationRequired;

    private boolean pushNotificationRequired;
    private boolean emailNotificationRequired;
    private boolean smsNotificationRequired;
    private String[] requiredNotificationChannels;

    private String[] requiredPaymentMethods;
    private boolean installmentSupportRequired;
    private boolean discountSupportRequired;
    private boolean reminderSystemRequired;

    private String[] requiredIntegrations;
    private boolean apiAccessRequired;
    private boolean webhookSupportRequired;
    private String[] requiredThirdPartyServices;

    private String[] requiredComplianceStandards;
    private boolean gdprComplianceRequired;
    private boolean kvkkComplianceRequired;
    private String[] requiredCertifications;

    private String[] requiredUserRoles;
    private boolean mobileAppRequired;
    private boolean webAppRequired;
    private String[] requiredAccessibilityFeatures;

    private boolean horizontalScalingRequired;
    private boolean verticalScalingRequired;
    private Integer growthProjection;
    private String scalabilityRequirement; // LOW, MEDIUM, HIGH, ENTERPRISE

    private Integer locationWeight;
    private Integer costWeight;
    private Integer featureWeight;
    private Integer performanceWeight;
    private Integer supportWeight;
    private Integer securityWeight;
}
