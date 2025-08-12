package com.smartshopai.controller;

import com.smartshopai.domain.dto.PaymentDto;
import com.smartshopai.domain.dto.PaymentStatistics;
import com.smartshopai.domain.dto.MonthlyPaymentSummary;
import com.smartshopai.domain.dto.YearlyPaymentSummary;
import com.smartshopai.service.PaymentService;
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

import java.time.YearMonth;
import java.util.List;


@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Payment Management", description = "APIs for managing payments and dues")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new payment/dues record", description = "Admin only - Create a new payment or dues record")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto) {
        log.info("Creating new payment for user: {}", paymentDto.getUserId());
        PaymentDto createdPayment = paymentService.createPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update payment record", description = "Admin only - Update an existing payment record")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable String id, @RequestBody PaymentDto paymentDto) {
        log.info("Updating payment with ID: {}", id);
        PaymentDto updatedPayment = paymentService.updatePayment(id, paymentDto);
        return ResponseEntity.ok(updatedPayment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Get payment details by ID")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable String id) {
        log.debug("Getting payment by ID: {}", id);
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all payments", description = "Admin only - Get all payments with pagination")
    public ResponseEntity<Page<PaymentDto>> getAllPayments(Pageable pageable) {
        log.debug("Getting all payments with pagination");
        Page<PaymentDto> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get payments by user ID", description = "Get all payments for a specific user")
    public ResponseEntity<List<PaymentDto>> getPaymentsByUserId(@PathVariable String userId) {
        log.debug("Getting payments for user: {}", userId);
        List<PaymentDto> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/apartment/{apartmentNumber}")
    @Operation(summary = "Get payments by apartment number", description = "Get all payments for a specific apartment")
    public ResponseEntity<List<PaymentDto>> getPaymentsByApartmentNumber(@PathVariable String apartmentNumber) {
        log.debug("Getting payments for apartment: {}", apartmentNumber);
        List<PaymentDto> payments = paymentService.getPaymentsByApartmentNumber(apartmentNumber);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/building/{buildingNumber}")
    @Operation(summary = "Get payments by building number", description = "Get all payments for a specific building")
    public ResponseEntity<List<PaymentDto>> getPaymentsByBuildingNumber(@PathVariable String buildingNumber) {
        log.debug("Getting payments for building: {}", buildingNumber);
        List<PaymentDto> payments = paymentService.getPaymentsByBuildingNumber(buildingNumber);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/month/{dueMonth}")
    @Operation(summary = "Get payments by due month", description = "Get all payments for a specific month")
    public ResponseEntity<List<PaymentDto>> getPaymentsByDueMonth(@PathVariable YearMonth dueMonth) {
        log.debug("Getting payments for month: {}", dueMonth);
        List<PaymentDto> payments = paymentService.getPaymentsByDueMonth(dueMonth);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/monthly-summary/{year}/{month}")
    @Operation(summary = "Get monthly payment summary", description = "Get monthly payment summary with totals and statistics")
    public ResponseEntity<MonthlyPaymentSummary> getMonthlyPaymentSummary(
            @PathVariable Integer year,
            @PathVariable Integer month) {
        log.debug("Getting monthly payment summary for {}/{}", month, year);
        MonthlyPaymentSummary summary = paymentService.getMonthlyPaymentSummary(year, month);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/yearly-summary/{year}")
    @Operation(summary = "Get yearly payment summary", description = "Get yearly payment summary with monthly breakdown")
    public ResponseEntity<YearlyPaymentSummary> getYearlyPaymentSummary(@PathVariable Integer year) {
        log.debug("Getting yearly payment summary for {}", year);
        YearlyPaymentSummary summary = paymentService.getYearlyPaymentSummary(year);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue payments", description = "Get all overdue payments")
    public ResponseEntity<List<PaymentDto>> getOverduePayments() {
        log.debug("Getting overdue payments");
        List<PaymentDto> payments = paymentService.getOverduePayments();
        return ResponseEntity.ok(payments);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update payment status", description = "Admin only - Update payment status")
    public ResponseEntity<PaymentDto> updatePaymentStatus(
            @PathVariable String id,
            @RequestParam String status) {
        log.info("Updating payment status to {} for payment ID: {}", status, id);
        PaymentDto updatedPayment = paymentService.updatePaymentStatus(id, 
            com.smartshopai.domain.entity.Payment.PaymentStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(updatedPayment);
    }

    @PostMapping("/{id}/receipt")
    @Operation(summary = "Record payment receipt", description = "Record payment receipt with document upload")
    public ResponseEntity<PaymentDto> recordPaymentReceipt(
            @PathVariable String id,
            @RequestParam String receiptUrl,
            @RequestParam String paymentMethod) {
        log.info("Recording payment receipt for payment ID: {}", id);
        PaymentDto updatedPayment = paymentService.recordPaymentReceipt(id, receiptUrl,
            com.smartshopai.domain.entity.Payment.PaymentMethod.valueOf(paymentMethod.toUpperCase()));
        return ResponseEntity.ok(updatedPayment);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get payment statistics", description = "Admin only - Get payment statistics")
    public ResponseEntity<PaymentStatistics> getPaymentStatistics() {
        log.debug("Getting payment statistics");
        PaymentStatistics statistics = paymentService.getPaymentStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete payment", description = "Admin only - Delete a payment record")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) {
        log.info("Deleting payment with ID: {}", id);
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search payments", description = "Search payments with various criteria")
    public ResponseEntity<List<PaymentDto>> searchPayments(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String apartmentNumber,
            @RequestParam(required = false) String buildingNumber,
            @RequestParam(required = false) String paymentType,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String minAmount,
            @RequestParam(required = false) String maxAmount,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        log.debug("Searching payments with criteria");
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/{id}/installment")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create payment installment", description = "Admin only - Create payment installment plan")
    public ResponseEntity<PaymentDto> createPaymentInstallment(
            @PathVariable String id,
            @RequestParam Integer totalInstallments,
            @RequestParam String paymentPlan) {
        log.info("Creating payment installment plan for payment ID: {}", id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/discount")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Apply payment discount", description = "Admin only - Apply discount to payment")
    public ResponseEntity<PaymentDto> applyPaymentDiscount(
            @PathVariable String id,
            @RequestParam String discountAmount,
            @RequestParam String discountReason) {
        log.info("Applying discount to payment ID: {}", id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reminder")
    @Operation(summary = "Send payment reminder", description = "Send payment reminder notification")
    public ResponseEntity<Void> sendPaymentReminder(@PathVariable String id) {
        log.info("Sending payment reminder for payment ID: {}", id);
        return ResponseEntity.ok().build();
    }
}
