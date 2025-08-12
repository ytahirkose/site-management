package com.smartshopai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteCapacityInfo {

    private String siteId;
    private String siteName;
    private String siteCode;

    private UserCapacity userCapacity;

    private BuildingCapacity buildingCapacity;

    private ApartmentCapacity apartmentCapacity;

    private StorageCapacity storageCapacity;

    private PerformanceCapacity performanceCapacity;

    private String[] recommendations;
    private String[] warnings;
    private String[] actions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCapacity {
        private Integer currentUsers;
        private Integer maxUsers;
        private Integer availableSlots;
        private Double usagePercentage;
        private String status; // OK, WARNING, CRITICAL
        private Integer newUsersThisMonth;
        private Integer projectedGrowth;
        private Integer monthsUntilFull;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuildingCapacity {
        private Integer currentBuildings;
        private Integer maxBuildings;
        private Integer availableSlots;
        private Double usagePercentage;
        private String status;
        private Integer newBuildingsThisYear;
        private Integer projectedGrowth;
        private Integer yearsUntilFull;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApartmentCapacity {
        private Integer currentApartments;
        private Integer maxApartments;
        private Integer availableSlots;
        private Double usagePercentage;
        private String status;
        private Integer newApartmentsThisYear;
        private Integer projectedGrowth;
        private Integer yearsUntilFull;
        private Integer averageApartmentsPerBuilding;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StorageCapacity {
        private Long currentStorageUsed;
        private Long maxStorageAllowed;
        private Long availableStorage;
        private Double usagePercentage;
        private String status;
        private Long projectedStorageNeeded;
        private Integer monthsUntilFull;
        private String[] storageOptimizationTips;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerformanceCapacity {
        private Integer currentConcurrentUsers;
        private Integer maxConcurrentUsers;
        private Integer availableConcurrentSlots;
        private Double usagePercentage;
        private String status;
        private Integer peakUsageTime;
        private Integer averageResponseTime;
        private String[] performanceOptimizationTips;
    }
}
