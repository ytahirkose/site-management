package com.smartshopai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueStatistics {

    private long totalIssues;
    private long openIssues;
    private long inProgressIssues;
    private long resolvedIssues;
    private long urgentIssues;
    private long criticalIssues;
}
