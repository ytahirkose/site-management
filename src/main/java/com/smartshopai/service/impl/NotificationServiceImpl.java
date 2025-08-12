package com.smartshopai.service.impl;

import com.smartshopai.domain.dto.NotificationDto;
import com.smartshopai.domain.dto.UserDto;
import com.smartshopai.domain.entity.Notification;
import com.smartshopai.domain.entity.User;
import com.smartshopai.exception.ResourceNotFoundException;
import com.smartshopai.repository.NotificationRepository;
import com.smartshopai.domain.mapper.NotificationMapper;
import com.smartshopai.service.NotificationService;
import com.smartshopai.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void sendNotificationToUser(String userId, NotificationDto notification) {
        log.info("Sending notification to user: {}", userId);
        
        Notification notificationEntity = notificationMapper.toEntity(notification);
        notificationEntity.setTargetUserId(userId);
        notificationEntity.setCreatedAt(LocalDateTime.now());
        notificationEntity.setUpdatedAt(LocalDateTime.now());
        
        notificationRepository.save(notificationEntity);
        
        
        log.info("Notification sent to user: {}", userId);
    }

    @Override
    public void sendNotificationToUsers(List<String> userIds, NotificationDto notification) {
        log.info("Sending notification to {} users", userIds.size());
        
        userIds.forEach(userId -> sendNotificationToUser(userId, notification));
    }

    @Override
    public void sendNotificationToAllUsers(NotificationDto notification) {
        log.info("Sending notification to all users");
        
        List<UserDto> usersWithFcmToken = userService.getUsersWithFcmToken();
        usersWithFcmToken.forEach(user -> {
            if (user.getFcmToken() != null) {
                sendNotificationToUser(user.getId(), notification);
            }
        });
    }

    @Override
    public void sendNotificationToUsersByRole(String role, NotificationDto notification) {
        log.info("Sending notification to users with role: {}", role);
        
        List<UserDto> usersByRole = userService.getUsersByRole(User.Role.valueOf(role.toUpperCase()));
        usersByRole.forEach(user -> {
            if (user.getFcmToken() != null) {
                sendNotificationToUser(user.getId(), notification);
            }
        });
    }

    @Override
    public void sendNotificationToUsersByApartment(String apartmentNumber, NotificationDto notification) {
        log.info("Sending notification to users in apartment: {}", apartmentNumber);
        
        List<UserDto> usersByApartment = userService.getUsersByApartmentNumber(apartmentNumber);
        usersByApartment.forEach(user -> {
            if (user.getFcmToken() != null) {
                sendNotificationToUser(user.getId(), notification);
            }
        });
    }

    @Override
    public void sendNotificationToUsersByBuilding(String buildingNumber, NotificationDto notification) {
        log.info("Sending notification to users in building: {}", buildingNumber);
        
        List<UserDto> usersByBuilding = userService.getUsersByBuildingNumber(buildingNumber);
        usersByBuilding.forEach(user -> {
            if (user.getFcmToken() != null) {
                sendNotificationToUser(user.getId(), notification);
            }
        });
    }

    @Override
    public void updateUserFcmToken(String userId, String fcmToken) {
        log.info("Updating FCM token for user: {}", userId);
        
        userService.updateFcmToken(userId, fcmToken);
        log.info("FCM token updated for user: {}", userId);
    }

    @Override
    public void removeUserFcmToken(String userId) {
        log.info("Removing FCM token for user: {}", userId);
        
        userService.updateFcmToken(userId, null);
        log.info("FCM token removed for user: {}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationHistory(String userId) {
        log.debug("Getting notification history for user: {}", userId);
        
        List<Notification> notifications = notificationRepository.findByTargetUserId(userId);
        return notificationMapper.toDtoList(notifications);
    }

    @Override
    public void markNotificationAsRead(String notificationId) {
        log.info("Marking notification as read: {}", notificationId);
        
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + notificationId));
        
        notification.setRead(true);
        notification.setReadAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        
        notificationRepository.save(notification);
        log.info("Notification marked as read: {}", notificationId);
    }

    public void sendIssueAlert(String issueTitle, String issueDescription, String reporterName) {
        log.info("Sending issue alert notification");
        
        NotificationDto issueAlert = NotificationDto.builder()
                .id(UUID.randomUUID().toString())
                .title("Yeni Arıza Bildirimi")
                .body(String.format("Arıza: %s\nAçıklama: %s\nBildiren: %s", 
                        issueTitle, issueDescription, reporterName))
                .type("ISSUE_ALERT")
                .targetRole("ADMIN")
                .notifyAdmin(true)
                .createdAt(LocalDateTime.now())
                .build();
        
        sendNotificationToUsersByRole("ADMIN", issueAlert);
        log.info("Issue alert notification sent to admin users");
    }

    public void sendPaymentReminder(String userId, String apartmentNumber, String amount, String dueDate) {
        log.info("Sending payment reminder to user: {}", userId);
        
        NotificationDto paymentReminder = NotificationDto.builder()
                .id(UUID.randomUUID().toString())
                .title("Aidat Hatırlatması")
                .body(String.format("Aidat tutarı: %s TL\nSon ödeme tarihi: %s\nApartman: %s", 
                        amount, dueDate, apartmentNumber))
                .type("PAYMENT_REMINDER")
                .targetUserId(userId)
                .createdAt(LocalDateTime.now())
                .build();
        
        sendNotificationToUser(userId, paymentReminder);
        log.info("Payment reminder sent to user: {}", userId);
    }

    public void sendAnnouncementNotification(String title, String content, String targetRole, 
                                          String targetApartment, String targetBuilding) {
        log.info("Sending announcement notification");
        
        NotificationDto announcement = NotificationDto.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .body(content)
                .type("ANNOUNCEMENT")
                .targetRole(targetRole)
                .targetApartment(targetApartment)
                .targetBuilding(targetBuilding)
                .createdAt(LocalDateTime.now())
                .build();
        
        if (targetRole != null) {
            sendNotificationToUsersByRole(targetRole, announcement);
        } else if (targetApartment != null) {
            sendNotificationToUsersByApartment(targetApartment, announcement);
        } else if (targetBuilding != null) {
            sendNotificationToUsersByBuilding(targetBuilding, announcement);
        } else {
            sendNotificationToAllUsers(announcement);
        }
        
        log.info("Announcement notification sent");
    }
}
