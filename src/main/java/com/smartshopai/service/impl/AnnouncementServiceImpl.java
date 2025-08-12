package com.smartshopai.service.impl;

import com.smartshopai.domain.dto.AnnouncementDto;
import com.smartshopai.domain.entity.Announcement;
import com.smartshopai.domain.entity.User;
import com.smartshopai.domain.mapper.AnnouncementMapper;
import com.smartshopai.exception.ResourceNotFoundException;
import com.smartshopai.repository.AnnouncementRepository;
import com.smartshopai.repository.UserRepository;
import com.smartshopai.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final AnnouncementMapper announcementMapper;

    @Override
    public AnnouncementDto createAnnouncement(AnnouncementDto announcementDto, String authorId) {
        log.info("Creating new announcement with title: {}", announcementDto.getTitle());

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + authorId));

        Announcement announcement = announcementMapper.toEntity(announcementDto);
        announcement.setAuthorId(authorId);
        announcement.setAuthorName(author.getFirstName() + " " + author.getLastName());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setUpdatedAt(LocalDateTime.now());

        if (announcement.getPublishDate() == null) {
            announcement.setPublishDate(LocalDateTime.now());
        }

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        log.info("Announcement created successfully with ID: {}", savedAnnouncement.getId());

        return announcementMapper.toDto(savedAnnouncement);
    }

    @Override
    public AnnouncementDto updateAnnouncement(String id, AnnouncementDto announcementDto) {
        log.info("Updating announcement with ID: {}", id);

        Announcement existingAnnouncement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with ID: " + id));

        announcementMapper.updateEntityFromDto(announcementDto, existingAnnouncement);
        existingAnnouncement.setUpdatedAt(LocalDateTime.now());

        Announcement updatedAnnouncement = announcementRepository.save(existingAnnouncement);
        log.info("Announcement updated successfully with ID: {}", updatedAnnouncement.getId());

        return announcementMapper.toDto(updatedAnnouncement);
    }

    @Override
    @Transactional(readOnly = true)
    public AnnouncementDto getAnnouncementById(String id) {
        log.debug("Getting announcement by ID: {}", id);
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with ID: " + id));
        return announcementMapper.toDto(announcement);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnouncementDto> getAllActiveAnnouncements(Pageable pageable) {
        log.debug("Getting all active announcements with pagination");
        return announcementRepository.findByIsActiveTrue(pageable).map(announcementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementDto> getAnnouncementsByTargetAudience(Announcement.TargetAudience targetAudience) {
        log.debug("Getting announcements by target audience: {}", targetAudience);
        return announcementMapper.toDtoList(announcementRepository.findActiveByTargetAudience(targetAudience));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementDto> getAnnouncementsByBuilding(String buildingNumber) {
        log.debug("Getting announcements by building: {}", buildingNumber);
        return announcementMapper.toDtoList(announcementRepository.findByIsActiveTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementDto> getAnnouncementsByAuthor(String authorId) {
        log.debug("Getting announcements by author: {}", authorId);
        return announcementMapper.toDtoList(announcementRepository.findByAuthorId(authorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementDto> getImportantAnnouncements() {
        log.debug("Getting important announcements");
        return announcementMapper.toDtoList(announcementRepository.findByIsImportantTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementDto> getAnnouncementsPublishedAfter(LocalDateTime date) {
        log.debug("Getting announcements published after: {}", date);
        return announcementMapper.toDtoList(announcementRepository.findByPublishDateAfter(date));
    }

    @Override
    public void deleteAnnouncement(String id) {
        log.info("Deleting announcement with ID: {}", id);

        if (!announcementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Announcement not found with ID: " + id);
        }

        announcementRepository.deleteById(id);
        log.info("Announcement deleted successfully with ID: {}", id);
    }

    @Override
    public void toggleAnnouncementStatus(String id, boolean isActive) {
        log.info("Toggling announcement status to {} for ID: {}", isActive, id);

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with ID: " + id));

        announcement.setActive(isActive);
        announcement.setUpdatedAt(LocalDateTime.now());

        announcementRepository.save(announcement);
        log.info("Announcement status toggled to {} for ID: {}", isActive, id);
    }

    @Override
    public void toggleImportantStatus(String id, boolean isImportant) {
        log.info("Toggling important status to {} for ID: {}", isImportant, id);

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with ID: " + id));

        announcement.setImportant(isImportant);
        announcement.setUpdatedAt(LocalDateTime.now());

        announcementRepository.save(announcement);
        log.info("Announcement important status toggled to {} for ID: {}", isImportant, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementDto> getExpiredAnnouncements() {
        log.debug("Getting expired announcements");
        return announcementMapper.toDtoList(announcementRepository.findExpiredAnnouncements(LocalDateTime.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementDto> getAnnouncementsForUser(String userId, String buildingNumber, boolean isAdmin) {
        log.debug("Getting announcements for user: {} in building: {}", userId, buildingNumber);

        if (isAdmin) {
            return announcementMapper.toDtoList(announcementRepository.findByIsActiveTrue());
        } else {
            List<Announcement.TargetAudience> allowedAudiences = List.of(
                    Announcement.TargetAudience.ALL,
                    Announcement.TargetAudience.RESIDENTS_ONLY
            );
            return announcementMapper.toDtoList(
                    announcementRepository.findActiveByTargetAudiencesAndDate(allowedAudiences, LocalDateTime.now())
            );
        }
    }
}
