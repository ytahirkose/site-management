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
public class SiteRecommendationDto {

    private String siteId;
    private String siteName;
    private String siteCode;
    private String city;
    private String country;
    private String status;

    private String recommendationType;
    private String description;
    private String reason;
    private Integer priority; // 1-5, 5 being highest
    private String category;

    private Double matchScore; // 0.0 - 1.0
    private String[] matchingCriteria;
    private String[] nonMatchingCriteria;

    private List<String> features;
    private List<String> amenities;
    private String[] supportedLanguages;
    private String[] supportedCurrencies;

    private BigDecimal estimatedMonthlyCost;
    private BigDecimal estimatedYearlyCost;
    private String pricingModel;
    private String[] includedServices;
    private String[] additionalServices;

    private Integer maxUsers;
    private Integer maxBuildings;
    private Integer maxApartments;
    private Long maxStorage;

    private Double uptimePercentage;
    private Integer averageResponseTime;
    private Integer maxConcurrentUsers;
    private String performanceRating;

    private Double averageRating;
    private Integer totalReviews;
    private String[] positiveFeedback;
    private String[] negativeFeedback;

    private String[] supportedFileTypes;
    private Integer maxFileSize;
    private boolean virusScanEnabled;
    private boolean backupEnabled;
    private String backupFrequency;

    private String supportEmail;
    private String supportPhone;
    private String[] supportLanguages;
    private String supportHours;
    private Integer averageResponseTimeHours;

    private String[] nextSteps;
    private String[] requirements;
    private String[] considerations;
    private String estimatedImplementationTime;
}
