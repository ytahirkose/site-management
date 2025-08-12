package com.smartshopai.domain.dto;

import com.smartshopai.domain.entity.Issue;
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
public class IssueDto {

    private String id;
    private String title;
    private String description;
    private String reporterId;
    private String reporterName;
    private String apartmentNumber;
    private String buildingNumber;
    private Integer floorNumber;
    private Issue.IssueType issueType;
    private Issue.Priority priority;
    private Issue.IssueStatus status;
    private String assignedTo;
    private String assignedToName;
    private LocalDateTime estimatedCompletionDate;
    private LocalDateTime actualCompletionDate;
    private List<String> photos;
    private String adminNotes;
    private String resolutionNotes;
    private String costEstimate;
    private boolean isUrgent;
    private boolean notifyAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
