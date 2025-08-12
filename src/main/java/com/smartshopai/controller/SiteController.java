package com.smartshopai.controller;

import com.smartshopai.domain.dto.SiteDto;
import com.smartshopai.domain.dto.SiteStatistics;
import com.smartshopai.domain.dto.SiteSummaryDto;
import com.smartshopai.domain.dto.SiteCapacityInfo;
import com.smartshopai.domain.dto.SiteRecommendationDto;
import com.smartshopai.domain.dto.SiteRecommendationCriteria;
import com.smartshopai.service.SiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/sites")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Site Management", description = "Site management operations for multi-tenant architecture")
public class SiteController {

    private final SiteService siteService;


    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Create site", description = "Super Admin only - Create a new site")
    public ResponseEntity<SiteDto> createSite(
            @Valid @RequestBody SiteDto siteDto,
            @RequestParam String superAdminId) {
        
        log.info("Creating new site: {}", siteDto.getName());
        SiteDto createdSite = siteService.createSite(siteDto, superAdminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSite);
    }


    @GetMapping("/{siteId}")
    @Operation(summary = "Get site by ID", description = "Get site information by ID")
    public ResponseEntity<SiteDto> getSiteById(@PathVariable String siteId) {
        log.debug("Getting site by ID: {}", siteId);
        SiteDto site = siteService.getSiteById(siteId);
        return ResponseEntity.ok(site);
    }


    @GetMapping("/code/{siteCode}")
    @Operation(summary = "Get site by code", description = "Get site information by unique code")
    public ResponseEntity<SiteDto> getSiteByCode(@PathVariable String siteCode) {
        log.debug("Getting site by code: {}", siteCode);
        SiteDto site = siteService.getSiteByCode(siteCode);
        return ResponseEntity.ok(site);
    }


    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Get all sites", description = "Super Admin only - Get all sites in the system")
    public ResponseEntity<List<SiteDto>> getAllSites() {
        log.debug("Getting all sites");
        List<SiteDto> sites = siteService.getAllSites();
        return ResponseEntity.ok(sites);
    }


    @GetMapping("/active")
    @Operation(summary = "Get active sites", description = "Get all active sites")
    public ResponseEntity<List<SiteDto>> getActiveSites() {
        log.debug("Getting active sites");
        List<SiteDto> sites = siteService.getActiveSites();
        return ResponseEntity.ok(sites);
    }


    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Get sites by status", description = "Super Admin only - Get sites by status")
    public ResponseEntity<List<SiteDto>> getSitesByStatus(
            @PathVariable String status) {
        log.debug("Getting sites by status: {}", status);
        List<SiteDto> sites = siteService.getSitesByStatus(com.smartshopai.domain.entity.Site.SiteStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/super-admin/{superAdminId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Get sites by super admin", description = "Super Admin only - Get sites created by specific super admin")
    public ResponseEntity<List<SiteDto>> getSitesBySuperAdmin(@PathVariable String superAdminId) {
        log.debug("Getting sites by super admin: {}", superAdminId);
        List<SiteDto> sites = siteService.getSitesBySuperAdmin(superAdminId);
        return ResponseEntity.ok(sites);
    }

    @PutMapping("/{siteId}")
    @PreAuthorize("hasRole('ADMIN') and @siteSecurityService.isUserFromSameSite(#siteId)")
    @Operation(summary = "Update site", description = "Site Admin only - Update site information")
    public ResponseEntity<SiteDto> updateSite(
            @PathVariable String siteId,
            @Valid @RequestBody SiteDto siteDto,
            @RequestParam String adminId) {
        
        log.info("Updating site: {}", siteId);
        SiteDto updatedSite = siteService.updateSite(siteId, siteDto, adminId);
        return ResponseEntity.ok(updatedSite);
    }

    @PutMapping("/{siteId}/settings")
    @PreAuthorize("hasRole('ADMIN') and @siteSecurityService.isUserFromSameSite(#siteId)")
    @Operation(summary = "Update site settings", description = "Site Admin only - Update site settings")
    public ResponseEntity<SiteDto> updateSiteSettings(
            @PathVariable String siteId,
            @Valid @RequestBody SiteDto.SiteSettings settings,
            @RequestParam String adminId) {
        
        log.info("Updating site settings: {}", siteId);
        SiteDto updatedSite = siteService.updateSiteSettings(siteId, settings, adminId);
        return ResponseEntity.ok(updatedSite);
    }

    @PutMapping("/{siteId}/address")
    @PreAuthorize("hasRole('ADMIN') and @siteSecurityService.isUserFromSameSite(#siteId)")
    @Operation(summary = "Update site address", description = "Site Admin only - Update site address")
    public ResponseEntity<SiteDto> updateSiteAddress(
            @PathVariable String siteId,
            @Valid @RequestBody SiteDto.Address address,
            @RequestParam String adminId) {
        
        log.info("Updating site address: {}", siteId);
        SiteDto updatedSite = siteService.updateSiteAddress(siteId, address, adminId);
        return ResponseEntity.ok(updatedSite);
    }

    @PutMapping("/{siteId}/contact")
    @PreAuthorize("hasRole('ADMIN') and @siteSecurityService.isUserFromSameSite(#siteId)")
    @Operation(summary = "Update site contact info", description = "Site Admin only - Update site contact information")
    public ResponseEntity<SiteDto> updateSiteContactInfo(
            @PathVariable String siteId,
            @Valid @RequestBody SiteDto.ContactInfo contactInfo,
            @RequestParam String adminId) {
        
        log.info("Updating site contact info: {}", siteId);
        SiteDto updatedSite = siteService.updateSiteContactInfo(siteId, contactInfo, adminId);
        return ResponseEntity.ok(updatedSite);
    }

    @PostMapping("/{siteId}/activate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Activate site", description = "Super Admin only - Activate a site")
    public ResponseEntity<SiteDto> activateSite(
            @PathVariable String siteId,
            @RequestParam String superAdminId) {
        
        log.info("Activating site: {}", siteId);
        SiteDto activatedSite = siteService.activateSite(siteId, superAdminId);
        return ResponseEntity.ok(activatedSite);
    }

    @PostMapping("/{siteId}/deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Deactivate site", description = "Super Admin only - Deactivate a site")
    public ResponseEntity<SiteDto> deactivateSite(
            @PathVariable String siteId,
            @RequestParam String superAdminId) {
        
        log.info("Deactivating site: {}", siteId);
        SiteDto deactivatedSite = siteService.deactivateSite(siteId, superAdminId);
        return ResponseEntity.ok(deactivatedSite);
    }

    @PostMapping("/{siteId}/suspend")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Suspend site", description = "Super Admin only - Suspend a site")
    public ResponseEntity<SiteDto> suspendSite(
            @PathVariable String siteId,
            @RequestParam String superAdminId,
            @RequestParam String reason) {
        
        log.info("Suspending site: {} with reason: {}", siteId, reason);
        SiteDto suspendedSite = siteService.suspendSite(siteId, superAdminId, reason);
        return ResponseEntity.ok(suspendedSite);
    }

    @DeleteMapping("/{siteId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Delete site", description = "Super Admin only - Delete a site")
    public ResponseEntity<Void> deleteSite(
            @PathVariable String siteId,
            @RequestParam String superAdminId) {
        
        log.info("Deleting site: {}", siteId);
        siteService.deleteSite(siteId, superAdminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{siteId}/statistics")
    @PreAuthorize("hasRole('ADMIN') and @siteSecurityService.isUserFromSameSite(#siteId)")
    @Operation(summary = "Get site statistics", description = "Site Admin only - Get site statistics")
    public ResponseEntity<SiteStatistics> getSiteStatistics(@PathVariable String siteId) {
        log.debug("Getting site statistics: {}", siteId);
        SiteStatistics statistics = siteService.getSiteStatistics(siteId);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "Get sites by city", description = "Get sites by city name")
    public ResponseEntity<List<SiteDto>> getSitesByCity(@PathVariable String city) {
        log.debug("Getting sites by city: {}", city);
        List<SiteDto> sites = siteService.getSitesByCity(city);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/country/{country}")
    @Operation(summary = "Get sites by country", description = "Get sites by country name")
    public ResponseEntity<List<SiteDto>> getSitesByCountry(@PathVariable String country) {
        log.debug("Getting sites by country: {}", country);
        List<SiteDto> sites = siteService.getSitesByCountry(country);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/timezone/{timezone}")
    @Operation(summary = "Get sites by timezone", description = "Get sites by timezone")
    public ResponseEntity<List<SiteDto>> getSitesByTimezone(@PathVariable String timezone) {
        log.debug("Getting sites by timezone: {}", timezone);
        List<SiteDto> sites = siteService.getSitesByTimezone(timezone);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/language/{language}")
    @Operation(summary = "Get sites by language", description = "Get sites by language")
    public ResponseEntity<List<SiteDto>> getSitesByLanguage(@PathVariable String language) {
        log.debug("Getting sites by language: {}", language);
        List<SiteDto> sites = siteService.getSitesByLanguage(language);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/currency/{currency}")
    @Operation(summary = "Get sites by currency", description = "Get sites by currency")
    public ResponseEntity<List<SiteDto>> getSitesByCurrency(@PathVariable String currency) {
        log.debug("Getting sites by currency: {}", currency);
        List<SiteDto> sites = siteService.getSitesByCurrency(currency);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/auto-payment/enabled")
    @Operation(summary = "Get sites with auto payment", description = "Get sites with auto payment enabled")
    public ResponseEntity<List<SiteDto>> getSitesWithAutoPaymentEnabled() {
        log.debug("Getting sites with auto payment enabled");
        List<SiteDto> sites = siteService.getSitesWithAutoPaymentEnabled();
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/notification/enabled")
    @Operation(summary = "Get sites with notification", description = "Get sites with notification enabled")
    public ResponseEntity<List<SiteDto>> getSitesWithNotificationEnabled() {
        log.debug("Getting sites with notification enabled");
        List<SiteDto> sites = siteService.getSitesWithNotificationEnabled();
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/file-upload/enabled")
    @Operation(summary = "Get sites with file upload", description = "Get sites with file upload enabled")
    public ResponseEntity<List<SiteDto>> getSitesWithFileUploadEnabled() {
        log.debug("Getting sites with file upload enabled");
        List<SiteDto> sites = siteService.getSitesWithFileUploadEnabled();
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Get sites summary", description = "Super Admin only - Get sites summary for dashboard")
    public ResponseEntity<List<SiteSummaryDto>> getSitesSummary() {
        log.debug("Getting sites summary");
        List<SiteSummaryDto> summary = siteService.getSitesSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/{siteId}/capacity")
    @PreAuthorize("hasRole('ADMIN') and @siteSecurityService.isUserFromSameSite(#siteId)")
    @Operation(summary = "Check site capacity", description = "Site Admin only - Check site capacity limits")
    public ResponseEntity<SiteCapacityInfo> checkSiteCapacity(@PathVariable String siteId) {
        log.debug("Checking site capacity: {}", siteId);
        SiteCapacityInfo capacityInfo = siteService.checkSiteCapacity(siteId);
        return ResponseEntity.ok(capacityInfo);
    }

    @PostMapping("/recommendations")
    @Operation(summary = "Get site recommendations", description = "Get site recommendations based on criteria")
    public ResponseEntity<List<SiteRecommendationDto>> getSiteRecommendations(
            @Valid @RequestBody SiteRecommendationCriteria criteria) {
        log.debug("Getting site recommendations");
        List<SiteRecommendationDto> recommendations = siteService.getSiteRecommendations(criteria);
        return ResponseEntity.ok(recommendations);
    }
}
