package com.smartshopai.service;

import com.smartshopai.domain.dto.PaymentDto;
import com.smartshopai.domain.dto.PaymentStatistics;
import com.smartshopai.domain.dto.MonthlyPaymentSummary;
import com.smartshopai.domain.dto.YearlyPaymentSummary;
import com.smartshopai.domain.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface PaymentService {

    PaymentDto createPayment(PaymentDto paymentDto);

    PaymentDto updatePayment(String id, PaymentDto paymentDto);

    Optional<PaymentDto> getPaymentById(String id);

    Page<PaymentDto> getAllPayments(Pageable pageable);

    List<PaymentDto> getPaymentsByUserId(String userId);

    List<PaymentDto> getPaymentsByApartmentNumber(String apartmentNumber);

    List<PaymentDto> getPaymentsByBuildingNumber(String buildingNumber);

    List<PaymentDto> getPaymentsByDueMonth(YearMonth dueMonth);

    List<PaymentDto> getOverduePayments();

    PaymentDto updatePaymentStatus(String id, Payment.PaymentStatus status);

    PaymentDto recordPaymentReceipt(String id, String receiptUrl, Payment.PaymentMethod paymentMethod);

    BigDecimal calculateLateFee(String paymentId);

    void deletePayment(String id);

    PaymentStatistics getPaymentStatistics();

    MonthlyPaymentSummary getMonthlyPaymentSummary(Integer year, Integer month);

    YearlyPaymentSummary getYearlyPaymentSummary(Integer year);

    List<PaymentDto> searchPayments(String userId, String apartmentNumber, String buildingNumber,
                                   String paymentType, String paymentMethod, String status,
                                   String minAmount, String maxAmount, String startDate, String endDate);

    PaymentDto createPaymentInstallment(String paymentId, Integer totalInstallments, String paymentPlan);

    PaymentDto applyPaymentDiscount(String paymentId, String discountAmount, String discountReason);

    void sendPaymentReminder(String paymentId);
}
