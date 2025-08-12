package com.smartshopai.repository;

import com.smartshopai.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByTargetUserId(String targetUserId);

    List<Notification> findByTargetRole(String targetRole);

    List<Notification> findByTargetApartment(String targetApartment);

    List<Notification> findByTargetBuilding(String targetBuilding);

    List<Notification> findByType(String type);

    List<Notification> findByTargetUserIdAndIsReadFalse(String targetUserId);

    List<Notification> findByDeliveryStatus(Notification.DeliveryStatus deliveryStatus);

    @Query("{ 'createdAt' : { $gte: ?0, $lte: ?1 } }")
    List<Notification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Notification> findByTargetUserId(String targetUserId, Pageable pageable);

    Page<Notification> findByTargetRole(String targetRole, Pageable pageable);

    long countByTargetUserIdAndIsReadFalse(String targetUserId);

    long countByDeliveryStatus(Notification.DeliveryStatus deliveryStatus);

    @Query("{ 'deliveryStatus' : 'FAILED', 'retryCount' : { $lt: 3 } }")
    List<Notification> findFailedNotificationsForRetry();

    List<Notification> findByTargetUserIdAndType(String targetUserId, String type);

    List<Notification> findByTargetUserIdAndIsRead(String targetUserId, boolean isRead);
}
