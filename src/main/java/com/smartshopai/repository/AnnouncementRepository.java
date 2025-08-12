package com.smartshopai.repository;

import com.smartshopai.domain.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String> {

    List<Announcement> findByIsActiveTrue();

    List<Announcement> findByTargetAudience(Announcement.TargetAudience targetAudience);

    List<Announcement> findByAuthorId(String authorId);



    List<Announcement> findByIsImportantTrue();

    List<Announcement> findByPublishDateAfter(LocalDateTime date);

    @Query("{'isActive': true, 'targetAudience': ?0}")
    List<Announcement> findActiveByTargetAudience(Announcement.TargetAudience targetAudience);

    Page<Announcement> findByIsActiveTrue(Pageable pageable);

    @Query("{'isActive': true, 'targetAudience': {$in: ?0}, 'publishDate': {$lte: ?1}}")
    List<Announcement> findActiveByTargetAudiencesAndDate(List<Announcement.TargetAudience> audiences, LocalDateTime date);

    @Query("{'expiryDate': {$lt: ?0}}")
    List<Announcement> findExpiredAnnouncements(LocalDateTime currentDate);

    List<Announcement> findBySiteId(String siteId);



    List<Announcement> findBySiteIdAndTargetAudience(String siteId, Announcement.TargetAudience targetAudience);



    List<Announcement> findBySiteIdAndIsActiveTrue(String siteId);



    List<Announcement> findBySiteIdAndAuthorId(String siteId, String authorId);
}
