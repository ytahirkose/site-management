package com.smartshopai.controller;

import com.smartshopai.domain.dto.IssueDto;
import com.smartshopai.domain.dto.IssueStatistics;
import com.smartshopai.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Issue Management", description = "APIs for managing maintenance issues and complaints")
@SecurityRequirement(name = "bearerAuth")
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    @Operation(summary = "Create new issue report", description = "Residents can create new issue reports")
    public ResponseEntity<IssueDto> createIssue(@RequestBody IssueDto issueDto) {
        log.info("Creating new issue report from user: {}", issueDto.getReporterId());
        IssueDto createdIssue = issueService.createIssue(issueDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIssue);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update issue details", description = "Update issue details (reporter or admin)")
    public ResponseEntity<IssueDto> updateIssue(@PathVariable String id, @RequestBody IssueDto issueDto) {
        log.info("Updating issue with ID: {}", id);
        IssueDto updatedIssue = issueService.updateIssue(id, issueDto);
        return ResponseEntity.ok(updatedIssue);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issue by ID", description = "Get issue details by ID")
    public ResponseEntity<IssueDto> getIssueById(@PathVariable String id) {
        log.debug("Getting issue by ID: {}", id);
        return issueService.getIssueById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all issues", description = "Admin only - Get all issues with pagination")
    public ResponseEntity<Page<IssueDto>> getAllIssues(Pageable pageable) {
        log.debug("Getting all issues with pagination");
        Page<IssueDto> issues = issueService.getAllIssues(pageable);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/reporter/{reporterId}")
    @Operation(summary = "Get issues by reporter ID", description = "Get all issues reported by a specific user")
    public ResponseEntity<List<IssueDto>> getIssuesByReporterId(@PathVariable String reporterId) {
        log.debug("Getting issues for reporter: {}", reporterId);
        List<IssueDto> issues = issueService.getIssuesByReporterId(reporterId);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/apartment/{apartmentNumber}")
    @Operation(summary = "Get issues by apartment number", description = "Get all issues for a specific apartment")
    public ResponseEntity<List<IssueDto>> getIssuesByApartmentNumber(@PathVariable String apartmentNumber) {
        log.debug("Getting issues for apartment: {}", apartmentNumber);
        List<IssueDto> issues = issueService.getIssuesByApartmentNumber(apartmentNumber);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/building/{buildingNumber}")
    @Operation(summary = "Get issues by building number", description = "Get all issues for a specific building")
    public ResponseEntity<List<IssueDto>> getIssuesByBuildingNumber(@PathVariable String buildingNumber) {
        log.debug("Getting issues for building: {}", buildingNumber);
        List<IssueDto> issues = issueService.getIssuesByBuildingNumber(buildingNumber);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get issues by status", description = "Get all issues with a specific status")
    public ResponseEntity<List<IssueDto>> getIssuesByStatus(@PathVariable String status) {
        log.debug("Getting issues with status: {}", status);
        List<IssueDto> issues = issueService.getIssuesByStatus(
            com.smartshopai.domain.entity.Issue.IssueStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get issues by priority", description = "Get all issues with a specific priority")
    public ResponseEntity<List<IssueDto>> getIssuesByPriority(@PathVariable String priority) {
        log.debug("Getting issues with priority: {}", priority);
        List<IssueDto> issues = issueService.getIssuesByPriority(
            com.smartshopai.domain.entity.Issue.Priority.valueOf(priority.toUpperCase()));
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/urgent")
    @Operation(summary = "Get urgent issues", description = "Get all urgent issues")
    public ResponseEntity<List<IssueDto>> getUrgentIssues() {
        log.debug("Getting urgent issues");
        List<IssueDto> issues = issueService.getUrgentIssues();
        return ResponseEntity.ok(issues);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update issue status", description = "Admin only - Update issue status")
    public ResponseEntity<IssueDto> updateIssueStatus(
            @PathVariable String id,
            @RequestParam String status) {
        log.info("Updating issue status to {} for issue ID: {}", status, id);
        IssueDto updatedIssue = issueService.updateIssueStatus(id,
            com.smartshopai.domain.entity.Issue.IssueStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(updatedIssue);
    }

    @PatchMapping("/{id}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign issue to staff", description = "Admin only - Assign issue to a staff member")
    public ResponseEntity<IssueDto> assignIssue(
            @PathVariable String id,
            @RequestParam String assignedTo,
            @RequestParam String assignedToName) {
        log.info("Assigning issue {} to staff member: {}", id, assignedTo);
        IssueDto updatedIssue = issueService.assignIssue(id, assignedTo, assignedToName);
        return ResponseEntity.ok(updatedIssue);
    }

    @PatchMapping("/{id}/admin-notes")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add admin notes", description = "Admin only - Add admin notes to issue")
    public ResponseEntity<IssueDto> addAdminNotes(
            @PathVariable String id,
            @RequestParam String adminNotes) {
        log.info("Adding admin notes to issue: {}", id);
        IssueDto updatedIssue = issueService.addAdminNotes(id, adminNotes);
        return ResponseEntity.ok(updatedIssue);
    }

    @PatchMapping("/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Resolve issue", description = "Admin only - Mark issue as resolved")
    public ResponseEntity<IssueDto> resolveIssue(
            @PathVariable String id,
            @RequestParam String resolutionNotes) {
        log.info("Resolving issue: {}", id);
        IssueDto updatedIssue = issueService.resolveIssue(id, resolutionNotes);
        return ResponseEntity.ok(updatedIssue);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get issue statistics", description = "Admin only - Get issue statistics")
    public ResponseEntity<IssueStatistics> getIssueStatistics() {
        log.debug("Getting issue statistics");
        IssueStatistics statistics = issueService.getIssueStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete issue", description = "Admin only - Delete an issue record")
    public ResponseEntity<Void> deleteIssue(@PathVariable String id) {
        log.info("Deleting issue with ID: {}", id);
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}
