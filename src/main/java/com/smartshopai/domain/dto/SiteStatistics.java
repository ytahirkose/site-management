package com.smartshopai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteStatistics {

    private String siteId;
    private String siteName;
    private String siteCode;
    private LocalDateTime generatedAt;

    private UserStatistics userStatistics;

    private PaymentStatistics paymentStatistics;

    private IssueStatistics issueStatistics;

    private AnnouncementStatistics announcementStatistics;

    private FileStatistics fileStatistics;

    private NotificationStatistics notificationStatistics;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserStatistics {
        private Integer totalUsers;
        private Integer activeUsers;
        private Integer inactiveUsers;
        private Integer siteAdmins;
        private Integer residents;
        private Integer newUsersThisMonth;
        private Integer newUsersThisYear;
        private Double userGrowthRate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentStatistics {
        private Integer totalPayments;
        private Integer pendingPayments;
        private Integer completedPayments;
        private Integer overduePayments;
        private BigDecimal totalAmount;
        private BigDecimal pendingAmount;
        private BigDecimal completedAmount;
        private BigDecimal overdueAmount;
        private BigDecimal totalLateFees;
        private Integer paymentsThisMonth;
        private Integer paymentsThisYear;
        private BigDecimal monthlyAverage;
        private BigDecimal yearlyTotal;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IssueStatistics {
        private Integer totalIssues;
        private Integer openIssues;
        private Integer inProgressIssues;
        private Integer resolvedIssues;
        private Integer closedIssues;
        private Integer urgentIssues;
        private Integer highPriorityIssues;
        private Integer mediumPriorityIssues;
        private Integer lowPriorityIssues;
        private Double averageResolutionTime;
        private Integer issuesThisMonth;
        private Integer issuesThisYear;
        private Double customerSatisfactionRate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnnouncementStatistics {
        private Integer totalAnnouncements;
        private Integer activeAnnouncements;
        private Integer expiredAnnouncements;
        private Integer importantAnnouncements;
        private Integer announcementsThisMonth;
        private Integer announcementsThisYear;
        private Integer totalReads;
        private Double averageReadRate;
        private Integer acknowledgmentsRequired;
        private Integer acknowledgmentsReceived;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileStatistics {
        private Integer totalFiles;
        private Long totalFileSize;
        private Integer filesThisMonth;
        private Integer filesThisYear;
        private Integer paymentReceipts;
        private Integer issuePhotos;
        private Integer announcementAttachments;
        private Integer userProfiles;
        private Double averageFileSize;
        private Integer virusScannedFiles;
        private Integer cleanFiles;
        private Integer infectedFiles;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationStatistics {
        private Integer totalNotifications;
        private Integer sentNotifications;
        private Integer deliveredNotifications;
        private Integer failedNotifications;
        private Integer pendingNotifications;
        private Integer notificationsThisMonth;
        private Integer notificationsThisYear;
        private Double deliveryRate;
        private Double readRate;
        private Integer pushNotifications;
        private Integer emailNotifications;
        private Integer smsNotifications;
    }
}
