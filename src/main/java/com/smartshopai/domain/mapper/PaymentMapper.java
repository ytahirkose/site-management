package com.smartshopai.domain.mapper;

import com.smartshopai.domain.dto.PaymentDto;
import com.smartshopai.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Named;
import org.mapstruct.IterableMapping;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    @Named("toDto")
    PaymentDto toDto(Payment payment);

    Payment toEntity(PaymentDto paymentDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<PaymentDto> toDtoList(List<Payment> payments);

    List<Payment> toEntityList(List<PaymentDto> paymentDtos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "dueMonth", ignore = true)
    @Mapping(target = "dueYear", ignore = true)
    @Mapping(target = "dueMonthNumber", ignore = true)
    void updateEntityFromDto(PaymentDto paymentDto, @MappingTarget Payment payment);

    @Mapping(target = "dueYear", expression = "java(payment.getDueMonth() != null ? payment.getDueMonth().getYear() : null)")
    @Mapping(target = "dueMonthNumber", expression = "java(payment.getDueMonth() != null ? payment.getDueMonth().getMonthValue() : null)")
    PaymentDto toDtoWithCalculatedFields(Payment payment);

    @Mapping(target = "installmentNumber", expression = "java(payment.getInstallmentNumber() != null ? payment.getInstallmentNumber() : 1)")
    @Mapping(target = "totalInstallments", expression = "java(payment.getTotalInstallments() != null ? payment.getTotalInstallments() : 1)")
    PaymentDto toDtoWithInstallmentInfo(Payment payment);

    @Mapping(target = "discountAmount", expression = "java(payment.getDiscountAmount() != null ? payment.getDiscountAmount() : java.math.BigDecimal.ZERO)")
    @Mapping(target = "discountReason", expression = "java(payment.getDiscountReason() != null ? payment.getDiscountReason() : \"\")")
    PaymentDto toDtoWithDiscountInfo(Payment payment);
}
