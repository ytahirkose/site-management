package com.smartshopai.repository;

import com.smartshopai.domain.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {

    List<Issue> findByReporterId(String reporterId);

    List<Issue> findByApartmentNumber(String apartmentNumber);

    List<Issue> findByBuildingNumber(String buildingNumber);

    List<Issue> findByStatus(Issue.IssueStatus status);

    List<Issue> findByPriority(Issue.Priority priority);

    List<Issue> findByIsUrgentTrue();

    List<Issue> findByIssueType(Issue.IssueType issueType);

    List<Issue> findByAssignedTo(String assignedTo);

    List<Issue> findByFloorNumber(Integer floorNumber);

    @Query("{ 'createdAt' : { $gte: ?0, $lte: ?1 } }")
    List<Issue> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'estimatedCompletionDate' : { $lte: ?0 } }")
    List<Issue> findByEstimatedCompletionDateBefore(LocalDateTime date);

    @Query("{ 'actualCompletionDate' : { $gte: ?0, $lte: ?1 } }")
    List<Issue> findByActualCompletionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'notifyAdmin' : true, 'status' : { $in: ['OPEN', 'IN_PROGRESS'] } }")
    List<Issue> findIssuesNeedingAdminAttention();

    @Query("{ 'costEstimate' : { $regex: ?0, $options: 'i' } }")
    List<Issue> findByCostEstimateContaining(String costEstimate);

    long countByStatus(Issue.IssueStatus status);

    long countByPriority(Issue.Priority priority);

    long countByReporterId(String reporterId);

    long countByIsUrgentTrue();

    Page<Issue> findAll(Pageable pageable);

    Page<Issue> findByReporterId(String reporterId, Pageable pageable);

    Page<Issue> findByApartmentNumber(String apartmentNumber, Pageable pageable);

    Page<Issue> findByBuildingNumber(String buildingNumber, Pageable pageable);

    Page<Issue> findByStatus(Issue.IssueStatus status, Pageable pageable);

    Page<Issue> findByPriority(Issue.Priority priority, Pageable pageable);
}
