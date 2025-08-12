package com.smartshopai.service;

import com.smartshopai.domain.dto.SiteDto;
import com.smartshopai.domain.dto.SiteStatistics;
import com.smartshopai.domain.dto.SiteSummaryDto;
import com.smartshopai.domain.dto.SiteCapacityInfo;
import com.smartshopai.domain.dto.SiteRecommendationDto;
import com.smartshopai.domain.dto.SiteRecommendationCriteria;
import com.smartshopai.domain.dto.SiteSearchCriteria;
import com.smartshopai.domain.dto.SiteUsageStatistics;
import com.smartshopai.domain.entity.Site;

import java.util.List;

public interface SiteService {

    SiteDto createSite(SiteDto siteDto, String superAdminId);

    SiteDto getSiteById(String siteId);

    SiteDto getSiteByCode(String siteCode);

    List<SiteDto> getAllSites();

    List<SiteDto> getActiveSites();

    List<SiteDto> getSitesByStatus(Site.SiteStatus status);

    List<SiteDto> getSitesBySuperAdmin(String superAdminId);

    SiteDto updateSite(String siteId, SiteDto siteDto, String adminId);

    SiteDto updateSiteSettings(String siteId, SiteDto.SiteSettings settings, String adminId);

    SiteDto updateSiteAddress(String siteId, SiteDto.Address address, String adminId);

    SiteDto updateSiteContactInfo(String siteId, SiteDto.ContactInfo contactInfo, String adminId);

    SiteDto activateSite(String siteId, String superAdminId);

    SiteDto deactivateSite(String siteId, String superAdminId);

    SiteDto suspendSite(String siteId, String superAdminId, String reason);

    void deleteSite(String siteId, String superAdminId);

    SiteStatistics getSiteStatistics(String siteId);

    boolean isSiteActive(String siteId);

    boolean hasUserAccessToSite(String userId, String siteId);

    List<SiteDto> getSitesByCity(String city);

    List<SiteDto> getSitesByCountry(String country);

    List<SiteDto> getSitesBySettings(SiteDto.SiteSettings settings);

    List<SiteDto> getSitesByTimezone(String timezone);

    List<SiteDto> getSitesByLanguage(String language);

    List<SiteDto> getSitesByCurrency(String currency);

    List<SiteDto> getSitesWithAutoPaymentEnabled();

    List<SiteDto> getSitesWithNotificationEnabled();

    List<SiteDto> getSitesWithFileUploadEnabled();

    List<SiteDto> getSitesByCreationDateRange(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);

    List<SiteDto> getSitesByUpdateDateRange(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);

    List<SiteDto> getSitesSupportingFileType(String fileType);

    List<SiteDto> getSitesByPaymentReminderDays(Integer reminderDays);

    List<SiteDto> getSitesByMaxUsersLimit(Integer maxUsers);

    List<SiteDto> getSitesByMaxBuildingsLimit(Integer maxBuildings);

    List<SiteDto> getSitesByMaxApartmentsPerBuildingLimit(Integer maxApartments);

    List<SiteDto> getSitesByMultipleCriteria(SiteSearchCriteria criteria);

    List<SiteSummaryDto> getSitesSummary();

    SiteUsageStatistics getSiteUsageStatistics(String siteId);

    boolean validateSiteSettings(SiteDto.SiteSettings settings);

    SiteCapacityInfo checkSiteCapacity(String siteId);

    List<SiteRecommendationDto> getSiteRecommendations(SiteRecommendationCriteria criteria);
}
