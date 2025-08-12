package com.smartshopai.repository;

import com.smartshopai.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    List<User> findByRolesContaining(User.Role role);

    List<User> findByApartmentNumberAndBuildingNumber(String apartmentNumber, String buildingNumber);

    List<User> findByBuildingNumber(String buildingNumber);

    @Query("{'fcmToken': {$exists: true, $ne: null}}")
    List<User> findUsersWithFcmToken();

    List<User> findByApartmentNumber(String apartmentNumber);

    List<User> findBySiteId(String siteId);

    List<User> findBySiteIdAndApartmentNumber(String siteId, String apartmentNumber);

    List<User> findBySiteIdAndBuildingNumber(String siteId, String buildingNumber);

    List<User> findBySiteIdAndSiteRole(String siteId, User.SiteRole siteRole);

    List<User> findBySiteIdAndEnabled(String siteId, boolean enabled);

    List<User> findByEnabledTrue();

    @Query("{'apartmentNumber': {$in: ?0}}")
    List<User> findByApartmentNumbers(List<String> apartmentNumbers);
}
