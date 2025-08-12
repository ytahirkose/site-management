package com.smartshopai.domain.dto;

import com.smartshopai.domain.entity.Site;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteSummaryDto {

    private String id;
    private String name;
    private String code;
    private String status;
    private String city;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer totalUsers;
    private Integer totalPayments;
    private Integer totalIssues;
    private Integer totalAnnouncements;
    private Integer totalFiles;

    private Integer activeUsers;
    private Integer pendingPayments;
    private Integer openIssues;
    private Integer activeAnnouncements;

    private Integer newUsersThisWeek;
    private Integer newPaymentsThisWeek;
    private Integer newIssuesThisWeek;
    private Integer newAnnouncementsThisWeek;

    private Double userGrowthRate;
    private Double paymentCompletionRate;
    private Double issueResolutionRate;
    private Double announcementReadRate;

    private Integer currentUsers;
    private Integer maxUsers;
    private Integer currentBuildings;
    private Integer maxBuildings;
    private Integer currentApartments;
    private Integer maxApartments;

    private boolean autoPaymentEnabled;
    private boolean notificationEnabled;
    private boolean fileUploadEnabled;
    private String timezone;
    private String language;
    private String currency;

    private String contactPhone;
    private String contactEmail;
    private String emergencyContact;
    private String emergencyPhone;
}
