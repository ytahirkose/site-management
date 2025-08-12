package com.smartshopai.service;

import com.smartshopai.domain.dto.IssueDto;
import com.smartshopai.domain.dto.IssueStatistics;
import com.smartshopai.domain.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    IssueDto createIssue(IssueDto issueDto);

    IssueDto updateIssue(String id, IssueDto issueDto);

    Optional<IssueDto> getIssueById(String id);

    Page<IssueDto> getAllIssues(Pageable pageable);

    List<IssueDto> getIssuesByReporterId(String reporterId);

    List<IssueDto> getIssuesByApartmentNumber(String apartmentNumber);

    List<IssueDto> getIssuesByBuildingNumber(String buildingNumber);

    List<IssueDto> getIssuesByStatus(Issue.IssueStatus status);

    List<IssueDto> getIssuesByPriority(Issue.Priority priority);

    List<IssueDto> getUrgentIssues();

    IssueDto updateIssueStatus(String id, Issue.IssueStatus status);

    IssueDto assignIssue(String id, String assignedTo, String assignedToName);

    IssueDto addAdminNotes(String id, String adminNotes);

    IssueDto addResolutionNotes(String id, String resolutionNotes);

    IssueDto resolveIssue(String id, String resolutionNotes);

    void deleteIssue(String id);

    IssueStatistics getIssueStatistics();
}
