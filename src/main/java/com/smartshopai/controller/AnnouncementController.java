package com.smartshopai.controller;

import com.smartshopai.domain.dto.AnnouncementDto;
import com.smartshopai.domain.entity.Announcement;
import com.smartshopai.service.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Announcements", description = "Announcement management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    @Operation(summary = "Get all active announcements", description = "Get all active announcements with pagination")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<Page<AnnouncementDto>> getAllAnnouncements(Pageable pageable) {
        log.debug("Getting all active announcements");
        Page<AnnouncementDto> announcements = announcementService.getAllActiveAnnouncements(pageable);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get announcement by ID", description = "Get announcement details by ID")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable String id) {
        log.debug("Getting announcement by ID: {}", id);
        AnnouncementDto announcement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(announcement);
    }

    @GetMapping("/important")
    @Operation(summary = "Get important announcements", description = "Get all important announcements")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<AnnouncementDto>> getImportantAnnouncements() {
        log.debug("Getting important announcements");
        List<AnnouncementDto> announcements = announcementService.getImportantAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/by-audience/{targetAudience}")
    @Operation(summary = "Get announcements by target audience", description = "Get announcements by specific target audience")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByTargetAudience(
            @PathVariable String targetAudience) {
        log.debug("Getting announcements by target audience: {}", targetAudience);
        Announcement.TargetAudience audience = Announcement.TargetAudience.valueOf(targetAudience.toUpperCase());
        List<AnnouncementDto> announcements = announcementService.getAnnouncementsByTargetAudience(audience);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/by-building/{buildingNumber}")
    @Operation(summary = "Get announcements by building", description = "Get announcements for specific building")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByBuilding(@PathVariable String buildingNumber) {
        log.debug("Getting announcements by building: {}", buildingNumber);
        List<AnnouncementDto> announcements = announcementService.getAnnouncementsByBuilding(buildingNumber);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/my-announcements")
    @Operation(summary = "Get user's announcements", description = "Get announcements created by current user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AnnouncementDto>> getMyAnnouncements(@AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Getting announcements for user: {}", userDetails.getUsername());
        List<AnnouncementDto> announcements = announcementService.getAnnouncementsByAuthor(userDetails.getUsername());
        return ResponseEntity.ok(announcements);
    }

    @PostMapping
    @Operation(summary = "Create new announcement", description = "Create a new announcement (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnnouncementDto> createAnnouncement(
            @Valid @RequestBody AnnouncementDto announcementDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Creating new announcement with title: {}", announcementDto.getTitle());
        AnnouncementDto createdAnnouncement = announcementService.createAnnouncement(announcementDto, userDetails.getUsername());
        return ResponseEntity.ok(createdAnnouncement);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update announcement", description = "Update existing announcement (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnnouncementDto> updateAnnouncement(
            @PathVariable String id,
            @Valid @RequestBody AnnouncementDto announcementDto) {
        log.info("Updating announcement with ID: {}", id);
        AnnouncementDto updatedAnnouncement = announcementService.updateAnnouncement(id, announcementDto);
        return ResponseEntity.ok(updatedAnnouncement);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete announcement", description = "Delete announcement by ID (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable String id) {
        log.info("Deleting announcement with ID: {}", id);
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/toggle-status")
    @Operation(summary = "Toggle announcement status", description = "Enable/disable announcement (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> toggleAnnouncementStatus(
            @PathVariable String id,
            @RequestParam boolean isActive) {
        log.info("Toggling announcement status to {} for ID: {}", isActive, id);
        announcementService.toggleAnnouncementStatus(id, isActive);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/toggle-important")
    @Operation(summary = "Toggle important status", description = "Mark/unmark announcement as important (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> toggleImportantStatus(
            @PathVariable String id,
            @RequestParam boolean isImportant) {
        log.info("Toggling important status to {} for ID: {}", isImportant, id);
        announcementService.toggleImportantStatus(id, isImportant);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/expired")
    @Operation(summary = "Get expired announcements", description = "Get all expired announcements (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AnnouncementDto>> getExpiredAnnouncements() {
        log.debug("Getting expired announcements");
        List<AnnouncementDto> announcements = announcementService.getExpiredAnnouncements();
        return ResponseEntity.ok(announcements);
    }
}
