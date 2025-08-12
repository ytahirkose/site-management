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
public class MonthlyPaymentSummary {

    private Integer year;
    private Integer month;
    private String monthName;
    private BigDecimal totalDues;
    private BigDecimal totalPaid;
    private BigDecimal totalPending;
    private BigDecimal totalOverdue;
    private BigDecimal totalLateFees;
    private long totalPayments;
    private long paidPayments;
    private long pendingPayments;
    private long overduePayments;
    private BigDecimal collectionRate;
    private BigDecimal averagePayment;
}
