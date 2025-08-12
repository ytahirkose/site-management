package com.smartshopai.service;

import com.smartshopai.domain.entity.User;
import com.smartshopai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SiteSecurityService {

    private final UserRepository userRepository;

    public boolean isUserFromSameSite(String userId, String siteId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                log.warn("User not found: {}", userId);
                return false;
            }

            boolean hasAccess = siteId.equals(user.getSiteId());
            log.debug("User {} access to site {}: {}", userId, siteId, hasAccess);
            return hasAccess;
        } catch (Exception e) {
            log.error("Error checking user site access: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean isSiteAdmin(String userId, String siteId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                return false;
            }

            boolean isSiteAdmin = siteId.equals(user.getSiteId()) && 
                                user.getSiteRole() == User.SiteRole.SITE_ADMIN;
            
            log.debug("User {} is site admin for site {}: {}", userId, siteId, isSiteAdmin);
            return isSiteAdmin;
        } catch (Exception e) {
            log.error("Error checking user site admin status: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean isSuperAdmin(String userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                return false;
            }

            boolean isSuperAdmin = user.getRoles().contains(User.Role.SUPER_ADMIN);
            log.debug("User {} is super admin: {}", userId, isSuperAdmin);
            return isSuperAdmin;
        } catch (Exception e) {
            log.error("Error checking user super admin status: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean canAccessSiteData(String userId, String siteId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                return false;
            }

            if (user.getRoles().contains(User.Role.SUPER_ADMIN)) {
                return true;
            }

            boolean canAccess = siteId.equals(user.getSiteId());
            log.debug("User {} can access site {} data: {}", userId, siteId, canAccess);
            return canAccess;
        } catch (Exception e) {
            log.error("Error checking user site data access: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean canManageSite(String userId, String siteId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                return false;
            }

            if (user.getRoles().contains(User.Role.SUPER_ADMIN)) {
                return true;
            }

            boolean canManage = siteId.equals(user.getSiteId()) && 
                              user.getSiteRole() == User.SiteRole.SITE_ADMIN;
            
            log.debug("User {} can manage site {}: {}", userId, siteId, canManage);
            return canManage;
        } catch (Exception e) {
            log.error("Error checking user site management access: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean canCreateSite(String userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                return false;
            }

            boolean canCreate = user.getRoles().contains(User.Role.SUPER_ADMIN);
            log.debug("User {} can create sites: {}", userId, canCreate);
            return canCreate;
        } catch (Exception e) {
            log.error("Error checking user site creation access: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean canDeleteSite(String userId, String siteId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                return false;
            }

            boolean canDelete = user.getRoles().contains(User.Role.SUPER_ADMIN);
            log.debug("User {} can delete site {}: {}", userId, siteId, canDelete);
            return canDelete;
        } catch (Exception e) {
            log.error("Error checking user site deletion access: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean canViewSiteStatistics(String userId, String siteId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                return false;
            }

            if (user.getRoles().contains(User.Role.SUPER_ADMIN)) {
                return true;
            }

            boolean canView = siteId.equals(user.getSiteId()) && 
                            user.getSiteRole() == User.SiteRole.SITE_ADMIN;
            
            log.debug("User {} can view site {} statistics: {}", userId, siteId, canView);
            return canView;
        } catch (Exception e) {
            log.error("Error checking user site statistics access: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean canUpdateSiteSettings(String userId, String siteId) {
        try {
            User user = userRepository.findById(userId)
                    .orElse(null);
            
            if (user == null) {
                return false;
            }

            if (user.getRoles().contains(User.Role.SUPER_ADMIN)) {
                return true;
            }

            boolean canUpdate = siteId.equals(user.getSiteId()) && 
                              user.getSiteRole() == User.SiteRole.SITE_ADMIN;
            
            log.debug("User {} can update site {} settings: {}", userId, siteId, canUpdate);
            return canUpdate;
        } catch (Exception e) {
            log.error("Error checking user site settings update access: {}", e.getMessage(), e);
            return false;
        }
    }
}
