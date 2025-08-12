package com.smartshopai.service.impl;

import com.smartshopai.domain.dto.PaymentDto;
import com.smartshopai.domain.dto.PaymentStatistics;
import com.smartshopai.domain.dto.MonthlyPaymentSummary;
import com.smartshopai.domain.dto.YearlyPaymentSummary;
import com.smartshopai.domain.entity.Payment;
import com.smartshopai.domain.mapper.PaymentMapper;
import com.smartshopai.exception.ResourceNotFoundException;
import com.smartshopai.repository.PaymentRepository;
import com.smartshopai.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        log.info("Creating new payment for user: {}", paymentDto.getUserId());

        Payment payment = paymentMapper.toEntity(paymentDto);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        
        if (payment.getStatus() == null) {
            payment.setStatus(Payment.PaymentStatus.PENDING);
        }
        if (payment.getDueMonth() == null && payment.getDueDate() != null) {
            payment.setDueMonth(YearMonth.from(payment.getDueDate()));
        }
        if (payment.getDueMonth() != null) {
            payment.setDueYear(payment.getDueMonth().getYear());
            payment.setDueMonthNumber(payment.getDueMonth().getMonthValue());
        }

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment created successfully with ID: {}", savedPayment.getId());

        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentDto updatePayment(String id, PaymentDto paymentDto) {
        log.info("Updating payment with ID: {}", id);

        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

        paymentMapper.updateEntityFromDto(paymentDto, existingPayment);
        existingPayment.setUpdatedAt(LocalDateTime.now());

        Payment updatedPayment = paymentRepository.save(existingPayment);
        log.info("Payment updated successfully with ID: {}", updatedPayment.getId());

        return paymentMapper.toDto(updatedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDto> getPaymentById(String id) {
        log.debug("Getting payment by ID: {}", id);
        return paymentRepository.findById(id).map(paymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDto> getAllPayments(Pageable pageable) {
        log.debug("Getting all payments with pagination");
        return paymentRepository.findAll(pageable).map(paymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByUserId(String userId) {
        log.debug("Getting payments for user: {}", userId);
        return paymentMapper.toDtoList(paymentRepository.findByUserId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByApartmentNumber(String apartmentNumber) {
        log.debug("Getting payments for apartment: {}", apartmentNumber);
        return paymentMapper.toDtoList(paymentRepository.findByApartmentNumber(apartmentNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByBuildingNumber(String buildingNumber) {
        log.debug("Getting payments for building: {}", buildingNumber);
        return paymentMapper.toDtoList(paymentRepository.findByBuildingNumber(buildingNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByDueMonth(YearMonth dueMonth) {
        log.debug("Getting payments for month: {}", dueMonth);
        return paymentMapper.toDtoList(paymentRepository.findByDueMonth(dueMonth));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getOverduePayments() {
        log.debug("Getting overdue payments");
        return paymentMapper.toDtoList(paymentRepository.findOverduePayments(LocalDate.now()));
    }

    @Override
    public PaymentDto updatePaymentStatus(String id, Payment.PaymentStatus status) {
        log.info("Updating payment status to {} for payment ID: {}", status, id);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());

        if (status == Payment.PaymentStatus.PAID) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        Payment updatedPayment = paymentRepository.save(payment);
        log.info("Payment status updated successfully for payment ID: {}", id);

        return paymentMapper.toDto(updatedPayment);
    }

    @Override
    public PaymentDto recordPaymentReceipt(String id, String receiptUrl, Payment.PaymentMethod paymentMethod) {
        log.info("Recording payment receipt for payment ID: {}", id);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

        payment.setReceiptUrl(receiptUrl);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(Payment.PaymentStatus.PAID);
        payment.setUpdatedAt(LocalDateTime.now());

        Payment updatedPayment = paymentRepository.save(payment);
        log.info("Payment receipt recorded successfully for payment ID: {}", id);

        return paymentMapper.toDto(updatedPayment);
    }

    @Override
    public BigDecimal calculateLateFee(String paymentId) {
        log.debug("Calculating late fee for payment ID: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        if (payment.getStatus() == Payment.PaymentStatus.PAID) {
            return BigDecimal.ZERO;
        }

        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(payment.getDueDate())) {
            long monthsLate = payment.getDueDate().until(currentDate).toTotalMonths();
            long daysLate = payment.getDueDate().until(currentDate).getDays();
            
            if (monthsLate > 0) {
                BigDecimal monthlyLateFee = payment.getAmount().multiply(BigDecimal.valueOf(0.05)).multiply(BigDecimal.valueOf(monthsLate));
                
                long remainingDays = daysLate % 30;
                BigDecimal dailyLateFee = payment.getAmount().multiply(BigDecimal.valueOf(0.002)).multiply(BigDecimal.valueOf(remainingDays));
                
                BigDecimal totalLateFee = monthlyLateFee.add(dailyLateFee);
                
                BigDecimal maxLateFee = payment.getAmount().multiply(BigDecimal.valueOf(0.5));
                if (totalLateFee.compareTo(maxLateFee) > 0) {
                    totalLateFee = maxLateFee;
                }
                
                payment.setLateFee(totalLateFee);
                payment.setTotalAmount(payment.getAmount().add(totalLateFee));
                payment.setUpdatedAt(LocalDateTime.now());
                paymentRepository.save(payment);
                
                log.info("Late fee calculated for payment {}: {} ({} months, {} days late)", 
                    paymentId, totalLateFee, monthsLate, remainingDays);
                
                return totalLateFee;
            }
        }

        return BigDecimal.ZERO;
    }

    @Override
    public void deletePayment(String id) {
        log.info("Deleting payment with ID: {}", id);

        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment not found with ID: " + id);
        }

        paymentRepository.deleteById(id);
        log.info("Payment deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentStatistics getPaymentStatistics() {
        log.debug("Getting payment statistics");

        long pendingCount = paymentRepository.countByStatus(Payment.PaymentStatus.PENDING);
        long paidCount = paymentRepository.countByStatus(Payment.PaymentStatus.PAID);
        long overdueCount = paymentRepository.countByStatus(Payment.PaymentStatus.OVERDUE);

        List<Payment> pendingPayments = paymentRepository.findByStatus(Payment.PaymentStatus.PENDING);
        List<Payment> paidPayments = paymentRepository.findByStatus(Payment.PaymentStatus.PAID);
        List<Payment> overduePayments = paymentRepository.findByStatus(Payment.PaymentStatus.OVERDUE);

        BigDecimal totalPending = pendingPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPaid = paidPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOverdue = overduePayments.stream()
                .map(Payment::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalLateFees = paymentRepository.findPaymentsWithLateFees().stream()
                .map(Payment::getLateFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return PaymentStatistics.builder()
                .totalPending(totalPending)
                .totalPaid(totalPaid)
                .totalOverdue(totalOverdue)
                .totalLateFees(totalLateFees)
                .pendingCount(pendingCount)
                .paidCount(paidCount)
                .overdueCount(overdueCount)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public MonthlyPaymentSummary getMonthlyPaymentSummary(Integer year, Integer month) {
        log.debug("Getting monthly payment summary for {}/{}", month, year);
        
        YearMonth yearMonth = YearMonth.of(year, month);
        List<Payment> monthlyPayments = paymentRepository.findByDueMonth(yearMonth);
        
        BigDecimal totalDues = monthlyPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalPaid = monthlyPayments.stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.PAID)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalPending = monthlyPayments.stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.PENDING)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalOverdue = monthlyPayments.stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.OVERDUE)
                .map(Payment::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalLateFees = monthlyPayments.stream()
                .map(Payment::getLateFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        long totalPayments = monthlyPayments.size();
        long paidPayments = monthlyPayments.stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.PAID)
                .count();
        long pendingPayments = monthlyPayments.stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.PENDING)
                .count();
        long overduePayments = monthlyPayments.stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.OVERDUE)
                .count();
        
        BigDecimal collectionRate = totalDues.compareTo(BigDecimal.ZERO) > 0 
                ? totalPaid.divide(totalDues, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;
        
        BigDecimal averagePayment = totalPayments > 0 
                ? totalDues.divide(BigDecimal.valueOf(totalPayments), 2, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;
        
        String monthName = yearMonth.getMonth().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault());
        
        return MonthlyPaymentSummary.builder()
                .year(year)
                .month(month)
                .monthName(monthName)
                .totalDues(totalDues)
                .totalPaid(totalPaid)
                .totalPending(totalPending)
                .totalOverdue(totalOverdue)
                .totalLateFees(totalLateFees)
                .totalPayments(totalPayments)
                .paidPayments(paidPayments)
                .pendingPayments(pendingPayments)
                .overduePayments(overduePayments)
                .collectionRate(collectionRate)
                .averagePayment(averagePayment)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public YearlyPaymentSummary getYearlyPaymentSummary(Integer year) {
        log.debug("Getting yearly payment summary for {}", year);
        
        List<MonthlyPaymentSummary> monthlyBreakdown = new ArrayList<>();
        BigDecimal totalDues = BigDecimal.ZERO;
        BigDecimal totalPaid = BigDecimal.ZERO;
        BigDecimal totalPending = BigDecimal.ZERO;
        BigDecimal totalOverdue = BigDecimal.ZERO;
        BigDecimal totalLateFees = BigDecimal.ZERO;
        long totalPayments = 0;
        long paidPayments = 0;
        long pendingPayments = 0;
        long overduePayments = 0;
        
        for (int month = 1; month <= 12; month++) {
            MonthlyPaymentSummary monthlySummary = getMonthlyPaymentSummary(year, month);
            monthlyBreakdown.add(monthlySummary);
            
            totalDues = totalDues.add(monthlySummary.getTotalDues());
            totalPaid = totalPaid.add(monthlySummary.getTotalPaid());
            totalPending = totalPending.add(monthlySummary.getTotalPending());
            totalOverdue = totalOverdue.add(monthlySummary.getTotalOverdue());
            totalLateFees = totalLateFees.add(monthlySummary.getTotalLateFees());
            totalPayments += monthlySummary.getTotalPayments();
            paidPayments += monthlySummary.getPaidPayments();
            pendingPayments += monthlySummary.getPendingPayments();
            overduePayments += monthlySummary.getOverduePayments();
        }
        
        BigDecimal collectionRate = totalDues.compareTo(BigDecimal.ZERO) > 0 
                ? totalPaid.divide(totalDues, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;
        
        BigDecimal averagePayment = totalPayments > 0 
                ? totalDues.divide(BigDecimal.valueOf(totalPayments), 2, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;
        
        return YearlyPaymentSummary.builder()
                .year(year)
                .totalDues(totalDues)
                .totalPaid(totalPaid)
                .totalPending(totalPending)
                .totalOverdue(totalOverdue)
                .totalLateFees(totalLateFees)
                .totalPayments(totalPayments)
                .paidPayments(paidPayments)
                .pendingPayments(pendingPayments)
                .overduePayments(overduePayments)
                .collectionRate(collectionRate)
                .averagePayment(averagePayment)
                .monthlyBreakdown(monthlyBreakdown)
                .build();
    }

    @Override
    public List<PaymentDto> searchPayments(String userId, String apartmentNumber, String buildingNumber,
                                         String paymentType, String paymentMethod, String status,
                                         String minAmount, String maxAmount, String startDate, String endDate) {
        log.debug("Searching payments with criteria");
        return new ArrayList<>();
    }

    @Override
    public PaymentDto createPaymentInstallment(String paymentId, Integer totalInstallments, String paymentPlan) {
        log.info("Creating payment installment plan for payment ID: {}", paymentId);
        return null;
    }

    @Override
    public PaymentDto applyPaymentDiscount(String paymentId, String discountAmount, String discountReason) {
        log.info("Applying discount to payment ID: {}", paymentId);
        return null;
    }

    @Override
    public void sendPaymentReminder(String paymentId) {
        log.info("Sending payment reminder for payment ID: {}", paymentId);
    }
}
