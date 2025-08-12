package com.smartshopai.service;

import com.smartshopai.domain.dto.AnnouncementDto;
import com.smartshopai.domain.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface AnnouncementService {


    AnnouncementDto createAnnouncement(AnnouncementDto announcementDto, String authorId);

    AnnouncementDto updateAnnouncement(String id, AnnouncementDto announcementDto);

    AnnouncementDto getAnnouncementById(String id);

    Page<AnnouncementDto> getAllActiveAnnouncements(Pageable pageable);

    List<AnnouncementDto> getAnnouncementsByTargetAudience(Announcement.TargetAudience targetAudience);

    List<AnnouncementDto> getAnnouncementsByBuilding(String buildingNumber);

    List<AnnouncementDto> getAnnouncementsByAuthor(String authorId);

    List<AnnouncementDto> getImportantAnnouncements();

    List<AnnouncementDto> getAnnouncementsPublishedAfter(LocalDateTime date);

    void deleteAnnouncement(String id);

    void toggleAnnouncementStatus(String id, boolean isActive);

    void toggleImportantStatus(String id, boolean isImportant);

    List<AnnouncementDto> getExpiredAnnouncements();

    List<AnnouncementDto> getAnnouncementsForUser(String userId, String buildingNumber, boolean isAdmin);
}
