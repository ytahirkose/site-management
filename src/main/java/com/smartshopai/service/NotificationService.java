package com.smartshopai.service;

import com.smartshopai.domain.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    void sendNotificationToUser(String userId, NotificationDto notification);

    void sendNotificationToUsers(List<String> userIds, NotificationDto notification);

    void sendNotificationToAllUsers(NotificationDto notification);

    void sendNotificationToUsersByRole(String role, NotificationDto notification);

    void sendNotificationToUsersByApartment(String apartmentNumber, NotificationDto notification);

    void sendNotificationToUsersByBuilding(String buildingNumber, NotificationDto notification);

    void updateUserFcmToken(String userId, String fcmToken);

    void removeUserFcmToken(String userId);

    List<NotificationDto> getNotificationHistory(String userId);

    void markNotificationAsRead(String notificationId);

    void sendIssueAlert(String issueTitle, String issueDescription, String reporterName);

    void sendPaymentReminder(String userId, String apartmentNumber, String amount, String dueDate);

    void sendAnnouncementNotification(String title, String content, String targetRole, String targetApartment, String targetBuilding);
}
