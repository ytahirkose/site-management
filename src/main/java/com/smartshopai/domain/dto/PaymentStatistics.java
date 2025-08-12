package com.smartshopai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatistics {

    private BigDecimal totalPending;
    private BigDecimal totalPaid;
    private BigDecimal totalOverdue;
    private BigDecimal totalLateFees;
    private long pendingCount;
    private long paidCount;
    private long overdueCount;
}
