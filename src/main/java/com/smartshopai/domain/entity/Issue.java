package com.smartshopai.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "issues")
public class Issue {

    @Id
    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
    private String description;

    @Field("reporter_id")
    @Indexed
    private String reporterId;

    @Field("reporter_name")
    private String reporterName;

    @Field("apartment_number")
    private String apartmentNumber;

    @Field("building_number")
    private String buildingNumber;

    @Field("floor_number")
    private Integer floorNumber;

    @NotNull(message = "Issue type is required")
    @Field("issue_type")
    private IssueType issueType;

    @Field("priority")
    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    @Field("status")
    @Builder.Default
    private IssueStatus status = IssueStatus.OPEN;

    @Field("assigned_to")
    private String assignedTo;

    @Field("assigned_to_name")
    private String assignedToName;

    @Field("estimated_completion_date")
    private LocalDateTime estimatedCompletionDate;

    @Field("actual_completion_date")
    private LocalDateTime actualCompletionDate;

    @Field("photos")
    private List<String> photos;

    @Field("admin_notes")
    private String adminNotes;

    @Field("resolution_notes")
    private String resolutionNotes;

    @Field("cost_estimate")
    private String costEstimate;

    @Field("is_urgent")
    @Builder.Default
    private boolean isUrgent = false;

    @Field("notify_admin")
    @Builder.Default
    private boolean notifyAdmin = true;

    @Field("notification_sent")
    @Builder.Default
    private boolean notificationSent = false;

    @Field("notification_sent_at")
    private LocalDateTime notificationSentAt;

    @Field("escalation_level")
    @Builder.Default
    private EscalationLevel escalationLevel = EscalationLevel.NORMAL;

    @Field("escalation_date")
    private LocalDateTime escalationDate;

    @Field("work_order_number")
    private String workOrderNumber;

    @Field("contractor_assigned")
    private String contractorAssigned;

    @Field("contractor_contact")
    private String contractorContact;

    @Field("materials_required")
    private String[] materialsRequired;

    @Field("estimated_cost")
    private String estimatedCost;

    @Field("actual_cost")
    private String actualCost;

    @Field("warranty_info")
    private String warrantyInfo;

    @Field("follow_up_required")
    @Builder.Default
    private boolean followUpRequired = false;

    @Field("follow_up_date")
    private LocalDateTime followUpDate;

    @Field("customer_satisfaction_rating")
    private Integer customerSatisfactionRating;

    @Field("customer_feedback")
    private String customerFeedback;

    @Field("site_id")
    @NotBlank
    private String siteId; // Site ID for multi-tenant

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    public enum IssueType {
        PLUMBING, ELECTRICAL, HVAC, STRUCTURAL, APPLIANCE, SECURITY, CLEANING, OTHER
    }

    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public enum IssueStatus {
        OPEN, IN_PROGRESS, PENDING_APPROVAL, RESOLVED, CLOSED, CANCELLED
    }

    public enum EscalationLevel {
        NORMAL, MEDIUM, HIGH, CRITICAL, URGENT
    }
}
