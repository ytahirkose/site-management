package com.smartshopai.repository;

import com.smartshopai.domain.entity.Site;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends MongoRepository<Site, String> {

    Optional<Site> findByCode(String code);

    Optional<Site> findByNameIgnoreCase(String name);

    List<Site> findByStatus(Site.SiteStatus status);

    List<Site> findByStatusIn(List<Site.SiteStatus> statuses);

    List<Site> findByCreatedBy(String createdBy);

    List<Site> findByAddressCityIgnoreCase(String city);

    List<Site> findByAddressCountryIgnoreCase(String country);

    @Query("{ 'settings.autoPaymentEnabled': ?0 }")
    List<Site> findByAutoPaymentEnabled(boolean autoPaymentEnabled);

    @Query("{ 'settings.notificationEnabled': ?0 }")
    List<Site> findByNotificationEnabled(boolean notificationEnabled);

    @Query("{ 'settings.timezone': ?0 }")
    List<Site> findByTimezone(String timezone);

    @Query("{ 'settings.language': ?0 }")
    List<Site> findByLanguage(String language);

    @Query("{ 'settings.currency': ?0 }")
    List<Site> findByCurrency(String currency);

    @Query("{ 'settings.maxUsers': { $gte: ?0 } }")
    List<Site> findByMaxUsersGreaterThanEqual(Integer maxUsers);

    @Query("{ 'settings.maxBuildings': { $gte: ?0 } }")
    List<Site> findByMaxBuildingsGreaterThanEqual(Integer maxBuildings);

    List<Site> findByStatusAndCreatedBy(Site.SiteStatus status, String createdBy);

    List<Site> findByStatusAndAddressCityIgnoreCase(Site.SiteStatus status, String city);

    @Query("{ 'settings.fileUploadEnabled': ?0 }")
    List<Site> findByFileUploadEnabled(boolean fileUploadEnabled);

    List<Site> findByStatusAndAddressCountryIgnoreCase(Site.SiteStatus status, String country);

    @Query("{ 'status': ?0, 'settings.timezone': ?1, 'settings.language': ?2 }")
    List<Site> findByStatusAndTimezoneAndLanguage(Site.SiteStatus status, String timezone, String language);

    @Query("{ 'settings.fileUploadEnabled': ?0, 'settings.maxFileSizeMB': { $gte: ?1 } }")
    List<Site> findByFileUploadEnabledAndMaxFileSizeGreaterThanEqual(boolean fileUploadEnabled, Integer maxFileSizeMB);

    @Query("{ 'settings.paymentReminderDays': { $gte: ?0 } }")
    List<Site> findByPaymentReminderDaysGreaterThanEqual(Integer paymentReminderDays);

    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 } }")
    List<Site> findByCreatedAtBetween(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);

    @Query("{ 'updatedAt': { $gte: ?0, $lte: ?1 } }")
    List<Site> findByUpdatedAtBetween(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);

    @Query("{ 'settings.allowedFileTypes': { $in: ?0 } }")
    List<Site> findByAllowedFileTypesContaining(List<String> fileTypes);

    @Query("{ 'status': ?0, 'settings.autoPaymentEnabled': ?1, 'settings.notificationEnabled': ?2 }")
    List<Site> findByStatusAndAutoPaymentEnabledAndNotificationEnabled(Site.SiteStatus status, boolean autoPaymentEnabled, boolean notificationEnabled);
}
