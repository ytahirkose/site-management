package com.smartshopai.service.impl;

import com.smartshopai.domain.dto.*;
import com.smartshopai.domain.entity.Site;
import com.smartshopai.domain.mapper.SiteMapper;
import com.smartshopai.repository.SiteRepository;
import com.smartshopai.service.SiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;
    private final SiteMapper siteMapper;

    @Override
    public SiteDto createSite(SiteDto siteDto, String superAdminId) {
        log.info("Creating new site: {} by super admin: {}", siteDto.getName(), superAdminId);
        
        Site site = siteMapper.toEntityForCreation(siteDto);
        site.setCreatedBy(superAdminId);
        site.setCreatedAt(LocalDateTime.now());
        site.setUpdatedAt(LocalDateTime.now());
        
        Site savedSite = siteRepository.save(site);
        log.info("Site created successfully: {}", savedSite.getId());
        
        return siteMapper.toDto(savedSite);
    }

    @Override
    public SiteDto getSiteById(String siteId) {
        log.debug("Getting site by ID: {}", siteId);
        
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        return siteMapper.toDto(site);
    }

    @Override
    public SiteDto getSiteByCode(String siteCode) {
        log.debug("Getting site by code: {}", siteCode);
        
        Site site = siteRepository.findByCode(siteCode)
                .orElseThrow(() -> new RuntimeException("Site not found with code: " + siteCode));
        
        return siteMapper.toDto(site);
    }

    @Override
    public List<SiteDto> getAllSites() {
        log.debug("Getting all sites");
        
        List<Site> sites = siteRepository.findAll();
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getActiveSites() {
        log.debug("Getting active sites");
        
        List<Site> sites = siteRepository.findByStatus(Site.SiteStatus.ACTIVE);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByStatus(Site.SiteStatus status) {
        log.debug("Getting sites by status: {}", status);
        
        List<Site> sites = siteRepository.findByStatus(status);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesBySuperAdmin(String superAdminId) {
        log.debug("Getting sites by super admin: {}", superAdminId);
        
        List<Site> sites = siteRepository.findByCreatedBy(superAdminId);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public SiteDto updateSite(String siteId, SiteDto siteDto, String adminId) {
        log.info("Updating site: {} by admin: {}", siteId, adminId);
        
        Site existingSite = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        Site updatedSite = siteMapper.toEntityForUpdate(siteDto);
        updatedSite.setId(siteId);
        updatedSite.setUpdatedAt(LocalDateTime.now());
        
        Site savedSite = siteRepository.save(updatedSite);
        log.info("Site updated successfully: {}", siteId);
        
        return siteMapper.toDto(savedSite);
    }

    @Override
    public SiteDto updateSiteSettings(String siteId, SiteDto.SiteSettings settings, String adminId) {
        log.info("Updating site settings: {} by admin: {}", siteId, adminId);
        
        Site existingSite = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        existingSite.setUpdatedAt(LocalDateTime.now());
        
        Site savedSite = siteRepository.save(existingSite);
        log.info("Site settings updated successfully: {}", siteId);
        
        return siteMapper.toDto(savedSite);
    }

    @Override
    public SiteDto updateSiteAddress(String siteId, SiteDto.Address address, String adminId) {
        log.info("Updating site address: {} by admin: {}", siteId, adminId);
        
        Site existingSite = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        existingSite.setAddress(siteMapper.toEntityForUpdate(siteMapper.toAddressDto(existingSite)).getAddress());
        existingSite.setUpdatedAt(LocalDateTime.now());
        
        Site savedSite = siteRepository.save(existingSite);
        log.info("Site address updated successfully: {}", siteId);
        
        return siteMapper.toDto(savedSite);
    }

    @Override
    public SiteDto updateSiteContactInfo(String siteId, SiteDto.ContactInfo contactInfo, String adminId) {
        log.info("Updating site contact info: {} by admin: {}", siteId, adminId);
        
        Site existingSite = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        existingSite.setContactInfo(siteMapper.toEntityForUpdate(siteMapper.toContactInfoDto(existingSite)).getContactInfo());
        existingSite.setUpdatedAt(LocalDateTime.now());
        
        Site savedSite = siteRepository.save(existingSite);
        log.info("Site contact info updated successfully: {}", siteId);
        
        return siteMapper.toDto(savedSite);
    }

    @Override
    public SiteDto activateSite(String siteId, String superAdminId) {
        log.info("Activating site: {} by super admin: {}", siteId, superAdminId);
        
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        site.setStatus(Site.SiteStatus.ACTIVE);
        site.setUpdatedAt(LocalDateTime.now());
        
        Site savedSite = siteRepository.save(site);
        log.info("Site activated successfully: {}", siteId);
        
        return siteMapper.toDto(savedSite);
    }

    @Override
    public SiteDto deactivateSite(String siteId, String superAdminId) {
        log.info("Deactivating site: {} by super admin: {}", siteId, superAdminId);
        
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        site.setStatus(Site.SiteStatus.INACTIVE);
        site.setUpdatedAt(LocalDateTime.now());
        
        Site savedSite = siteRepository.save(site);
        log.info("Site deactivated successfully: {}", siteId);
        
        return siteMapper.toDto(savedSite);
    }

    @Override
    public SiteDto suspendSite(String siteId, String superAdminId, String reason) {
        log.info("Suspending site: {} by super admin: {} with reason: {}", siteId, superAdminId, reason);
        
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        site.setStatus(Site.SiteStatus.SUSPENDED);
        site.setUpdatedAt(LocalDateTime.now());
        
        Site savedSite = siteRepository.save(site);
        log.info("Site suspended successfully: {}", siteId);
        
        return siteMapper.toDto(savedSite);
    }

    @Override
    public void deleteSite(String siteId, String superAdminId) {
        log.info("Deleting site: {} by super admin: {}", siteId, superAdminId);
        
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with ID: " + siteId));
        
        site.setStatus(Site.SiteStatus.DELETED);
        site.setDeletedAt(LocalDateTime.now());
        site.setUpdatedAt(LocalDateTime.now());
        
        siteRepository.save(site);
        log.info("Site deleted successfully: {}", siteId);
    }

    @Override
    public SiteStatistics getSiteStatistics(String siteId) {
        log.debug("Getting site statistics: {}", siteId);
        
        return SiteStatistics.builder()
                .siteId(siteId)
                .generatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public boolean isSiteActive(String siteId) {
        try {
            Site site = siteRepository.findById(siteId).orElse(null);
            return site != null && site.getStatus() == Site.SiteStatus.ACTIVE;
        } catch (Exception e) {
            log.error("Error checking site status: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean hasUserAccessToSite(String userId, String siteId) {
        return true; // Placeholder implementation
    }

    @Override
    public List<SiteDto> getSitesByCity(String city) {
        log.debug("Getting sites by city: {}", city);
        
        List<Site> sites = siteRepository.findByAddressCityIgnoreCase(city);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByCountry(String country) {
        log.debug("Getting sites by country: {}", country);
        
        List<Site> sites = siteRepository.findByAddressCountryIgnoreCase(country);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesBySettings(SiteDto.SiteSettings settings) {
        log.debug("Getting sites by settings");
        return List.of();
    }

    @Override
    public List<SiteDto> getSitesByTimezone(String timezone) {
        log.debug("Getting sites by timezone: {}", timezone);
        
        List<Site> sites = siteRepository.findByTimezone(timezone);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByLanguage(String language) {
        log.debug("Getting sites by language: {}", language);
        
        List<Site> sites = siteRepository.findByLanguage(language);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByCurrency(String currency) {
        log.debug("Getting sites by currency: {}", currency);
        
        List<Site> sites = siteRepository.findByCurrency(currency);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesWithAutoPaymentEnabled() {
        log.debug("Getting sites with auto payment enabled");
        
        List<Site> sites = siteRepository.findByAutoPaymentEnabled(true);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesWithNotificationEnabled() {
        log.debug("Getting sites with notification enabled");
        
        List<Site> sites = siteRepository.findByNotificationEnabled(true);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesWithFileUploadEnabled() {
        log.debug("Getting sites with file upload enabled");
        
        List<Site> sites = siteRepository.findByFileUploadEnabled(true);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByCreationDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Getting sites by creation date range: {} to {}", startDate, endDate);
        
        List<Site> sites = siteRepository.findByCreatedAtBetween(startDate, endDate);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByUpdateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Getting sites by update date range: {} to {}", startDate, endDate);
        
        List<Site> sites = siteRepository.findByUpdatedAtBetween(startDate, endDate);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesSupportingFileType(String fileType) {
        log.debug("Getting sites supporting file type: {}", fileType);
        
        List<Site> sites = siteRepository.findByAllowedFileTypesContaining(List.of(fileType));
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByPaymentReminderDays(Integer reminderDays) {
        log.debug("Getting sites by payment reminder days: {}", reminderDays);
        
        List<Site> sites = siteRepository.findByPaymentReminderDaysGreaterThanEqual(reminderDays);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByMaxUsersLimit(Integer maxUsers) {
        log.debug("Getting sites by max users limit: {}", maxUsers);
        
        List<Site> sites = siteRepository.findByMaxUsersGreaterThanEqual(maxUsers);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByMaxBuildingsLimit(Integer maxBuildings) {
        log.debug("Getting sites by max buildings limit: {}", maxBuildings);
        
        List<Site> sites = siteRepository.findByMaxBuildingsGreaterThanEqual(maxBuildings);
        return siteMapper.toDtoList(sites);
    }

    @Override
    public List<SiteDto> getSitesByMaxApartmentsPerBuildingLimit(Integer maxApartments) {
        log.debug("Getting sites by max apartments per building limit: {}", maxApartments);
        return List.of();
    }

    @Override
    public List<SiteDto> getSitesByMultipleCriteria(SiteSearchCriteria criteria) {
        log.debug("Getting sites by multiple criteria");
        return List.of();
    }

    @Override
    public List<SiteSummaryDto> getSitesSummary() {
        log.debug("Getting sites summary");
        
        return List.of();
    }

    @Override
    public SiteUsageStatistics getSiteUsageStatistics(String siteId) {
        log.debug("Getting site usage statistics: {}", siteId);
        return null;
    }

    @Override
    public boolean validateSiteSettings(SiteDto.SiteSettings settings) {
        log.debug("Validating site settings");
        return true;
    }

    @Override
    public SiteCapacityInfo checkSiteCapacity(String siteId) {
        log.debug("Checking site capacity: {}", siteId);
        return null;
    }

    @Override
    public List<SiteRecommendationDto> getSiteRecommendations(SiteRecommendationCriteria criteria) {
        log.debug("Getting site recommendations");
        return List.of();
    }
}
