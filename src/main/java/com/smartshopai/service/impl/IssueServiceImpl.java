package com.smartshopai.service.impl;

import com.smartshopai.domain.dto.IssueDto;
import com.smartshopai.domain.dto.IssueStatistics;
import com.smartshopai.domain.entity.Issue;
import com.smartshopai.domain.mapper.IssueMapper;
import com.smartshopai.exception.ResourceNotFoundException;
import com.smartshopai.repository.IssueRepository;
import com.smartshopai.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    @Override
    public IssueDto createIssue(IssueDto issueDto) {
        log.info("Creating new issue report from user: {}", issueDto.getReporterId());

        Issue issue = issueMapper.toEntity(issueDto);
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        
        if (issue.getStatus() == null) {
            issue.setStatus(Issue.IssueStatus.OPEN);
        }
        if (issue.getPriority() == null) {
            issue.setPriority(Issue.Priority.MEDIUM);
        }

        Issue savedIssue = issueRepository.save(issue);
        log.info("Issue created successfully with ID: {}", savedIssue.getId());

        return issueMapper.toDto(savedIssue);
    }

    @Override
    public IssueDto updateIssue(String id, IssueDto issueDto) {
        log.info("Updating issue with ID: {}", id);

        Issue existingIssue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with ID: " + id));

        issueMapper.updateEntityFromDto(issueDto, existingIssue);
        existingIssue.setUpdatedAt(LocalDateTime.now());

        Issue updatedIssue = issueRepository.save(existingIssue);
        log.info("Issue updated successfully with ID: {}", updatedIssue.getId());

        return issueMapper.toDto(updatedIssue);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IssueDto> getIssueById(String id) {
        log.debug("Getting issue by ID: {}", id);
        return issueRepository.findById(id).map(issueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IssueDto> getAllIssues(Pageable pageable) {
        log.debug("Getting all issues with pagination");
        return issueRepository.findAll(pageable).map(issueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueDto> getIssuesByReporterId(String reporterId) {
        log.debug("Getting issues for reporter: {}", reporterId);
        return issueMapper.toDtoList(issueRepository.findByReporterId(reporterId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueDto> getIssuesByApartmentNumber(String apartmentNumber) {
        log.debug("Getting issues for apartment: {}", apartmentNumber);
        return issueMapper.toDtoList(issueRepository.findByApartmentNumber(apartmentNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueDto> getIssuesByBuildingNumber(String buildingNumber) {
        log.debug("Getting issues for building: {}", buildingNumber);
        return issueMapper.toDtoList(issueRepository.findByBuildingNumber(buildingNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueDto> getIssuesByStatus(Issue.IssueStatus status) {
        log.debug("Getting issues with status: {}", status);
        return issueMapper.toDtoList(issueRepository.findByStatus(status));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueDto> getIssuesByPriority(Issue.Priority priority) {
        log.debug("Getting issues with priority: {}", priority);
        return issueMapper.toDtoList(issueRepository.findByPriority(priority));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueDto> getUrgentIssues() {
        log.debug("Getting urgent issues");
        return issueMapper.toDtoList(issueRepository.findByIsUrgentTrue());
    }

    @Override
    public IssueDto updateIssueStatus(String id, Issue.IssueStatus status) {
        log.info("Updating issue status to {} for issue ID: {}", status, id);

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with ID: " + id));

        issue.setStatus(status);
        issue.setUpdatedAt(LocalDateTime.now());

        if (status == Issue.IssueStatus.RESOLVED) {
            issue.setActualCompletionDate(LocalDateTime.now());
        }

        Issue updatedIssue = issueRepository.save(issue);
        log.info("Issue status updated successfully for issue ID: {}", id);

        return issueMapper.toDto(updatedIssue);
    }

    @Override
    public IssueDto assignIssue(String id, String assignedTo, String assignedToName) {
        log.info("Assigning issue {} to staff member: {}", id, assignedTo);

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with ID: " + id));

        issue.setAssignedTo(assignedTo);
        issue.setAssignedToName(assignedToName);
        issue.setStatus(Issue.IssueStatus.IN_PROGRESS);
        issue.setUpdatedAt(LocalDateTime.now());

        Issue updatedIssue = issueRepository.save(issue);
        log.info("Issue assigned successfully to staff member: {}", assignedTo);

        return issueMapper.toDto(updatedIssue);
    }

    @Override
    public IssueDto addAdminNotes(String id, String adminNotes) {
        log.info("Adding admin notes to issue: {}", id);

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with ID: " + id));

        issue.setAdminNotes(adminNotes);
        issue.setUpdatedAt(LocalDateTime.now());

        Issue updatedIssue = issueRepository.save(issue);
        log.info("Admin notes added successfully to issue: {}", id);

        return issueMapper.toDto(updatedIssue);
    }

    @Override
    public IssueDto addResolutionNotes(String id, String resolutionNotes) {
        log.info("Adding resolution notes to issue: {}", id);

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with ID: " + id));

        issue.setResolutionNotes(resolutionNotes);
        issue.setUpdatedAt(LocalDateTime.now());

        Issue updatedIssue = issueRepository.save(issue);
        log.info("Resolution notes added successfully to issue: {}", id);

        return issueMapper.toDto(updatedIssue);
    }

    @Override
    public IssueDto resolveIssue(String id, String resolutionNotes) {
        log.info("Resolving issue: {}", id);

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with ID: " + id));

        issue.setStatus(Issue.IssueStatus.RESOLVED);
        issue.setResolutionNotes(resolutionNotes);
        issue.setActualCompletionDate(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());

        Issue updatedIssue = issueRepository.save(issue);
        log.info("Issue resolved successfully: {}", id);

        return issueMapper.toDto(updatedIssue);
    }

    @Override
    public void deleteIssue(String id) {
        log.info("Deleting issue with ID: {}", id);

        if (!issueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Issue not found with ID: " + id);
        }

        issueRepository.deleteById(id);
        log.info("Issue deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public IssueStatistics getIssueStatistics() {
        log.debug("Getting issue statistics");

        long totalIssues = issueRepository.count();
        long openIssues = issueRepository.countByStatus(Issue.IssueStatus.OPEN);
        long inProgressIssues = issueRepository.countByStatus(Issue.IssueStatus.IN_PROGRESS);
        long resolvedIssues = issueRepository.countByStatus(Issue.IssueStatus.RESOLVED);
        long urgentIssues = issueRepository.countByIsUrgentTrue();
        long criticalIssues = issueRepository.countByPriority(Issue.Priority.CRITICAL);

        return IssueStatistics.builder()
                .totalIssues(totalIssues)
                .openIssues(openIssues)
                .inProgressIssues(inProgressIssues)
                .resolvedIssues(resolvedIssues)
                .urgentIssues(urgentIssues)
                .criticalIssues(criticalIssues)
                .build();
    }
}
