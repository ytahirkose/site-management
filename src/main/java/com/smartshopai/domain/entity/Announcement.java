package com.smartshopai.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "announcements")
public class Announcement {

    @Id
    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 2000, message = "Content must be between 10 and 2000 characters")
    private String content;

    @Field("author_id")
    private String authorId;

    @Field("author_name")
    private String authorName;

    @Builder.Default
    @Setter
    private boolean isActive = true;

    @Builder.Default
    @Setter
    private boolean isImportant = false;

    @Field("publish_date")
    private LocalDateTime publishDate;

    @Field("expiry_date")
    private LocalDateTime expiryDate;

    @Field("target_audience")
    private TargetAudience targetAudience;

    @Field("is_admin_only")
    @Builder.Default
    private boolean isAdminOnly = false;

    @Field("is_resident_visible")
    @Builder.Default
    private boolean isResidentVisible = true;

    @Field("priority_level")
    @Builder.Default
    private PriorityLevel priorityLevel = PriorityLevel.NORMAL;

    @Field("category")
    private String category;

    @Field("attachments")
    private String[] attachments;

    @Field("read_count")
    @Builder.Default
    private Integer readCount = 0;

    @Field("read_by_users")
    private String[] readByUsers;

    @Field("tags")
    private String[] tags;

    @Field("location_specific")
    private boolean locationSpecific;

    @Field("target_buildings")
    private String[] targetBuildings;

    @Field("target_apartments")
    private String[] targetApartments;

    @Field("site_id")
    @NotBlank
    private String siteId; // Site ID for multi-tenant

    @Field("requires_acknowledgment")
    @Builder.Default
    private boolean requiresAcknowledgment = false;

    @Field("acknowledged_by")
    private String[] acknowledgedBy;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    public enum TargetAudience {
        ALL, RESIDENTS_ONLY, ADMIN_ONLY
    }

    public enum PriorityLevel {
        LOW, NORMAL, HIGH, URGENT
    }

    public enum Priority {
        LOW, NORMAL, HIGH, URGENT
    }

    public enum Status {
        DRAFT, PUBLISHED, EXPIRED, ARCHIVED
    }
}
