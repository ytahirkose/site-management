package com.smartshopai.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Body is required")
    @Size(min = 10, max = 1000, message = "Body must be between 10 and 1000 characters")
    private String body;

    @Field("type")
    private String type;

    @Field("notification_category")
    private NotificationCategory notificationCategory;

    @Field("priority")
    @Builder.Default
    private NotificationPriority priority = NotificationPriority.NORMAL;

    @Field("target_user_id")
    @Indexed
    private String targetUserId;

    @Field("target_role")
    private String targetRole;

    @Field("target_apartment")
    private String targetApartment;

    @Field("target_building")
    private String targetBuilding;

    @Field("data")
    private Map<String, String> data;

    @Field("is_read")
    @Builder.Default
    private boolean isRead = false;

    @Field("read_at")
    private LocalDateTime readAt;

    @Field("sent_at")
    private LocalDateTime sentAt;

    @Field("delivery_status")
    @Builder.Default
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;

    @Field("fcm_message_id")
    private String fcmMessageId;

    @Field("retry_count")
    @Builder.Default
    private int retryCount = 0;

    @Field("scheduled_for")
    private LocalDateTime scheduledFor;

    @Field("is_scheduled")
    @Builder.Default
    private boolean isScheduled = false;

    @Field("notification_template")
    private String notificationTemplate;

    @Field("template_variables")
    private Map<String, String> templateVariables;

    @Field("action_required")
    @Builder.Default
    private boolean actionRequired = false;

    @Field("action_url")
    private String actionUrl;

    @Field("action_text")
    private String actionText;

    @Field("notification_group")
    private String notificationGroup;

    @Field("batch_id")
    private String batchId;

    @Field("delivery_channels")
    private String[] deliveryChannels;

    @Field("user_preferences_override")
    private boolean userPreferencesOverride;

    @Field("site_id")
    @NotBlank
    private String siteId; // Site ID for multi-tenant

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    public enum DeliveryStatus {
        PENDING, SENT, DELIVERED, FAILED, CANCELLED
    }

    public enum NotificationCategory {
        ISSUE_ALERT, PAYMENT_REMINDER, ANNOUNCEMENT, SYSTEM_NOTIFICATION, MAINTENANCE_UPDATE
    }

    public enum NotificationPriority {
        LOW, NORMAL, HIGH, URGENT, CRITICAL
    }
}
