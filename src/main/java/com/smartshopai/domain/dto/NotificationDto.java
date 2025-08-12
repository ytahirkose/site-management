package com.smartshopai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private String id;
    private String title;
    private String body;
    private String type;
    private String targetUserId;
    private String targetRole;
    private String targetApartment;
    private String targetBuilding;
    private Map<String, String> data;
    private boolean isRead;
    private boolean notifyAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}
