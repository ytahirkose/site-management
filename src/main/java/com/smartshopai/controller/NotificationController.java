package com.smartshopai.controller;

import com.smartshopai.domain.dto.NotificationDto;
import com.smartshopai.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Notification Management", description = "APIs for managing push notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send/user")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Send notification to user", description = "Admin only - Send notification to a specific user")
    public ResponseEntity<Void> sendNotificationToUser(
            @RequestParam String userId,
            @RequestBody NotificationDto notification) {
        log.info("Sending notification to user: {}", userId);
        notificationService.sendNotificationToUser(userId, notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Send notification to multiple users", description = "Admin only - Send notification to multiple users")
    public ResponseEntity<Void> sendNotificationToUsers(
            @RequestParam List<String> userIds,
            @RequestBody NotificationDto notification) {
        log.info("Sending notification to {} users", userIds.size());
        notificationService.sendNotificationToUsers(userIds, notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Send notification to all users", description = "Admin only - Send notification to all users with FCM tokens")
    public ResponseEntity<Void> sendNotificationToAllUsers(@RequestBody NotificationDto notification) {
        log.info("Sending notification to all users");
        notificationService.sendNotificationToAllUsers(notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Send notification to users by role", description = "Admin only - Send notification to users with specific role")
    public ResponseEntity<Void> sendNotificationToUsersByRole(
            @RequestParam String role,
            @RequestBody NotificationDto notification) {
        log.info("Sending notification to users with role: {}", role);
        notificationService.sendNotificationToUsersByRole(role, notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/apartment")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Send notification to users by apartment", description = "Admin only - Send notification to users in specific apartment")
    public ResponseEntity<Void> sendNotificationToUsersByApartment(
            @RequestParam String apartmentNumber,
            @RequestBody NotificationDto notification) {
        log.info("Sending notification to users in apartment: {}", apartmentNumber);
        notificationService.sendNotificationToUsersByApartment(apartmentNumber, notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/building")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Send notification to users by building", description = "Admin only - Send notification to users in specific building")
    public ResponseEntity<Void> sendNotificationToUsersByBuilding(
            @RequestParam String buildingNumber,
            @RequestBody NotificationDto notification) {
        log.info("Sending notification to users in building: {}", buildingNumber);
        notificationService.sendNotificationToUsersByBuilding(buildingNumber, notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fcm-token")
    @Operation(summary = "Update FCM token", description = "Update user's FCM token for push notifications")
    public ResponseEntity<Void> updateFcmToken(
            @RequestParam String userId,
            @RequestParam String fcmToken) {
        log.info("Updating FCM token for user: {}", userId);
        notificationService.updateUserFcmToken(userId, fcmToken);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/fcm-token")
    @Operation(summary = "Remove FCM token", description = "Remove user's FCM token")
    public ResponseEntity<Void> removeFcmToken(@RequestParam String userId) {
        log.info("Removing FCM token for user: {}", userId);
        notificationService.removeUserFcmToken(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{userId}")
    @Operation(summary = "Get notification history", description = "Get notification history for a specific user")
    public ResponseEntity<List<NotificationDto>> getNotificationHistory(@PathVariable String userId) {
        log.debug("Getting notification history for user: {}", userId);
        List<NotificationDto> notifications = notificationService.getNotificationHistory(userId);
        return ResponseEntity.ok(notifications);
    }

    @PatchMapping("/{notificationId}/read")
    @Operation(summary = "Mark notification as read", description = "Mark a notification as read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable String notificationId) {
        log.info("Marking notification as read: {}", notificationId);
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/issue-alert")
    @Operation(summary = "Send issue alert", description = "Send notification to admin when new issue is reported")
    public ResponseEntity<Void> sendIssueAlert(@RequestBody NotificationDto notification) {
        log.info("Sending issue alert notification");
        notificationService.sendNotificationToUsersByRole("ADMIN", notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auto/issue-alert")
    @Operation(summary = "Auto issue alert", description = "Automatically send notification to admin when new issue is reported")
    public ResponseEntity<Void> autoIssueAlert(
            @RequestParam String issueTitle,
            @RequestParam String issueDescription,
            @RequestParam String reporterName) {
        log.info("Auto-sending issue alert notification");
        notificationService.sendIssueAlert(issueTitle, issueDescription, reporterName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auto/payment-reminder")
    @Operation(summary = "Auto payment reminder", description = "Automatically send payment reminder notification")
    public ResponseEntity<Void> autoPaymentReminder(
            @RequestParam String userId,
            @RequestParam String apartmentNumber,
            @RequestParam String amount,
            @RequestParam String dueDate) {
        log.info("Auto-sending payment reminder notification");
        notificationService.sendPaymentReminder(userId, apartmentNumber, amount, dueDate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auto/announcement")
    @Operation(summary = "Auto announcement notification", description = "Automatically send announcement notification")
    public ResponseEntity<Void> autoAnnouncement(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(required = false) String targetRole,
            @RequestParam(required = false) String targetApartment,
            @RequestParam(required = false) String targetBuilding) {
        log.info("Auto-sending announcement notification");
        notificationService.sendAnnouncementNotification(title, content, targetRole, targetApartment, targetBuilding);
        return ResponseEntity.ok().build();
    }
}
