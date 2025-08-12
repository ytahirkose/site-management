package com.smartshopai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteUsageStatistics {

    private String siteId;
    private String siteName;
    private String siteCode;
    private LocalDateTime generatedAt;
    private String period; // DAILY, WEEKLY, MONTHLY, YEARLY

    private UserUsage userUsage;

    private StorageUsage storageUsage;

    private ApiUsage apiUsage;

    private FeatureUsage featureUsage;

    private PerformanceUsage performanceUsage;

    private CostUsage costUsage;

    private TrendAnalysis trendAnalysis;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserUsage {
        private Integer totalUsers;
        private Integer activeUsers;
        private Integer inactiveUsers;
        private Integer newUsers;
        private Integer deletedUsers;
        private Double userGrowthRate;
        private Integer peakConcurrentUsers;
        private Integer averageConcurrentUsers;
        private Map<String, Integer> usersByRole;
        private Map<String, Integer> usersByBuilding;
        private Map<String, Integer> usersByApartment;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StorageUsage {
        private Long totalStorageUsed;
        private Long totalStorageAllowed;
        private Long availableStorage;
        private Double usagePercentage;
        private Long storageGrowth;
        private Double storageGrowthRate;
        private Map<String, Long> storageByType;
        private Map<String, Long> storageByUser;
        private Map<String, Long> storageByMonth;
        private Long averageFileSize;
        private Integer totalFiles;
        private Integer newFilesThisPeriod;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiUsage {
        private Integer totalApiCalls;
        private Integer successfulApiCalls;
        private Integer failedApiCalls;
        private Double successRate;
        private Integer uniqueApiUsers;
        private Map<String, Integer> apiCallsByEndpoint;
        private Map<String, Integer> apiCallsByUser;
        private Map<String, Integer> apiCallsByHour;
        private Map<String, Integer> apiCallsByDay;
        private Integer peakApiCallsPerHour;
        private Integer averageApiCallsPerHour;
        private Double averageResponseTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureUsage {
        private Integer totalPayments;
        private Integer totalIssues;
        private Integer totalAnnouncements;
        private Integer totalNotifications;
        private Map<String, Integer> featureUsageByUser;
        private Map<String, Integer> featureUsageByDay;
        private Map<String, Integer> featureUsageByHour;
        private Double paymentCompletionRate;
        private Double issueResolutionRate;
        private Double notificationDeliveryRate;
        private Integer autoPaymentsEnabled;
        private Integer notificationsEnabled;
        private Integer fileUploadsEnabled;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerformanceUsage {
        private Double uptimePercentage;
        private Integer totalDowntimeMinutes;
        private Double averageResponseTime;
        private Integer peakResponseTime;
        private Integer minResponseTime;
        private Map<String, Double> responseTimeByEndpoint;
        private Map<String, Double> responseTimeByHour;
        private Map<String, Double> responseTimeByDay;
        private Integer totalErrors;
        private Integer totalWarnings;
        private Map<String, Integer> errorsByType;
        private Map<String, Integer> errorsByHour;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CostUsage {
        private BigDecimal totalCost;
        private BigDecimal storageCost;
        private BigDecimal apiCost;
        private BigDecimal bandwidthCost;
        private BigDecimal supportCost;
        private BigDecimal additionalCosts;
        private Map<String, BigDecimal> costByMonth;
        private Map<String, BigDecimal> costByFeature;
        private Map<String, BigDecimal> costByUser;
        private Double costGrowthRate;
        private BigDecimal projectedCost;
        private String costOptimizationRecommendations;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrendAnalysis {
        private Map<String, Double> userGrowthTrend;
        private Map<String, Double> storageGrowthTrend;
        private Map<String, Double> apiUsageTrend;
        private Map<String, Double> costTrend;
        private Map<String, Double> performanceTrend;
        private String[] positiveTrends;
        private String[] negativeTrends;
        private String[] recommendations;
        private String[] predictions;
        private Double overallTrendScore;
    }
}
