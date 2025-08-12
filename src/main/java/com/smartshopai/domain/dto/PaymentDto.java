package com.smartshopai.domain.dto;

import com.smartshopai.domain.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private String id;
    private String userId;
    private String apartmentNumber;
    private String buildingNumber;
    private LocalDate dueDate;
    private YearMonth dueMonth;
    private Integer dueYear;
    private Integer dueMonthNumber;
    private BigDecimal amount;
    private String description;
    private Payment.PaymentType paymentType;
    private Payment.PaymentStatus status;
    private Payment.PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private String receiptUrl;
    private String adminNotes;
    private BigDecimal lateFee;
    private BigDecimal totalAmount;
    private Integer installmentNumber;
    private Integer totalInstallments;
    private Payment.PaymentPlan paymentPlan;
    private BigDecimal discountAmount;
    private String discountReason;
    private Integer paymentRemindersSent;
    private LocalDateTime lastReminderSent;
    private boolean autoPaymentEnabled;
    private Payment.PaymentMethod autoPaymentMethod;
    private String paymentMethodDetails;
    private String bankName;
    private String accountNumber;
    private String transactionReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
