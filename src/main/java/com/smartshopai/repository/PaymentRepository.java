package com.smartshopai.repository;

import com.smartshopai.domain.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> findByUserId(String userId);

    List<Payment> findByApartmentNumber(String apartmentNumber);

    List<Payment> findByBuildingNumber(String buildingNumber);

    List<Payment> findByDueMonth(YearMonth dueMonth);

    List<Payment> findByStatus(Payment.PaymentStatus status);

    @Query("{ 'dueDate' : { $lt: ?0 }, 'status' : 'PENDING' }")
    List<Payment> findOverduePayments(LocalDate currentDate);

    List<Payment> findByPaymentType(Payment.PaymentType paymentType);

    List<Payment> findByPaymentMethod(Payment.PaymentMethod paymentMethod);

    @Query("{ 'lateFee' : { $gt: 0 } }")
    List<Payment> findPaymentsWithLateFees();

    @Query("{ 'amount' : { $gte: ?0, $lte: ?1 } }")
    List<Payment> findByAmountRange(BigDecimal minAmount, BigDecimal maxAmount);

    @Query("{ 'dueDate' : { $gte: ?0, $lte: ?1 } }")
    List<Payment> findByDueDateRange(LocalDate startDate, LocalDate endDate);

    long countByStatus(Payment.PaymentStatus status);

    long countByUserId(String userId);

    Page<Payment> findAll(Pageable pageable);

    Page<Payment> findByUserId(String userId, Pageable pageable);

    Page<Payment> findByApartmentNumber(String apartmentNumber, Pageable pageable);

    Page<Payment> findByBuildingNumber(String buildingNumber, Pageable pageable);

    @Query("{ 'dueYear' : ?0, 'dueMonthNumber' : ?1 }")
    List<Payment> findByYearAndMonth(Integer year, Integer month);

    @Query("{ 'dueYear' : ?0 }")
    List<Payment> findByYear(Integer year);

    @Query("{ 'dueMonthNumber' : ?0 }")
    List<Payment> findByMonthNumber(Integer month);

    List<Payment> findByPaymentMethodDetailsContainingIgnoreCase(String paymentMethodDetails);

    List<Payment> findByBankNameContainingIgnoreCase(String bankName);

    List<Payment> findByTransactionReferenceContainingIgnoreCase(String transactionReference);

    List<Payment> findByInstallmentNumber(Integer installmentNumber);

    List<Payment> findByPaymentPlan(Payment.PaymentPlan paymentPlan);

    @Query("{ 'discountAmount' : { $gt: 0 } }")
    List<Payment> findPaymentsWithDiscounts();

    @Query("{ 'paymentRemindersSent' : { $gte: ?0 } }")
    List<Payment> findByReminderCountGreaterThanEqual(Integer reminderCount);

    List<Payment> findByAutoPaymentEnabled(boolean autoPaymentEnabled);

    @Query("{ 'estimatedCompletionDate' : { $gte: ?0, $lte: ?1 } }")
    List<Payment> findByEstimatedCompletionDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
