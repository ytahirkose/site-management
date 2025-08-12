package com.smartshopai.domain.dto;

import com.smartshopai.domain.entity.Announcement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class AnnouncementDto {

    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 2000, message = "Content must be between 10 and 2000 characters")
    private String content;

    private String authorId;
    private String authorName;
    private boolean isActive;
    private boolean isImportant;
    private LocalDateTime publishDate;
    private LocalDateTime expiryDate;
    private Announcement.TargetAudience targetAudience;
    private List<String> attachments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String buildingNumber;
    private String apartmentNumber;
    private boolean isExpired;
    private long daysUntilExpiry;
}
