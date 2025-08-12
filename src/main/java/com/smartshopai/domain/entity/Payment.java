package com.smartshopai.domain.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payment {

    @Id
    private String id;

    @Field("user_id")
    @Indexed
    private String userId;

    @Field("apartment_number")
    private String apartmentNumber;

    @Field("building_number")
    private String buildingNumber;

    @NotNull(message = "Due date is required")
    @Field("due_date")
    private LocalDate dueDate;

    @Field("due_month")
    private YearMonth dueMonth;

    @Field("due_year")
    private Integer dueYear;

    @Field("due_month_number")
    private Integer dueMonthNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Field("description")
    private String description;

    @Field("payment_type")
    private PaymentType paymentType;

    @Field("status")
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @Field("payment_method")
    private PaymentMethod paymentMethod;

    @Field("payment_method_details")
    private String paymentMethodDetails;

    @Field("bank_name")
    private String bankName;

    @Field("account_number")
    private String accountNumber;

    @Field("transaction_reference")
    private String transactionReference;

    @Field("payment_date")
    private LocalDateTime paymentDate;

    @Field("receipt_url")
    private String receiptUrl;

    @Field("admin_notes")
    private String adminNotes;

    @Field("late_fee")
    private BigDecimal lateFee;

    @Field("total_amount")
    private BigDecimal totalAmount;

    @Field("installment_number")
    private Integer installmentNumber;

    @Field("total_installments")
    private Integer totalInstallments;

    @Field("payment_plan")
    private PaymentPlan paymentPlan;

    @Field("discount_amount")
    private BigDecimal discountAmount;

    @Field("discount_reason")
    private String discountReason;

    @Field("payment_reminders_sent")
    @Builder.Default
    private Integer paymentRemindersSent = 0;

    @Field("last_reminder_sent")
    private LocalDateTime lastReminderSent;

    @Field("auto_payment_enabled")
    @Builder.Default
    private boolean autoPaymentEnabled = false;

    @Field("auto_payment_method")
    private PaymentMethod autoPaymentMethod;

    @Field("site_id")
    @NotBlank
    private String siteId; // Site ID for multi-tenant

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    public enum PaymentType {
        DUES, MAINTENANCE, ELECTRICITY, WATER, GAS, OTHER
    }

    public enum PaymentStatus {
        PENDING, PAID, OVERDUE, CANCELLED
    }

    public enum PaymentMethod {
        BANK_TRANSFER, CASH, CHECK, ONLINE_PAYMENT
    }

    public enum PaymentPlan {
        MONTHLY, QUARTERLY, SEMI_ANNUAL, ANNUAL, CUSTOM
    }
}
