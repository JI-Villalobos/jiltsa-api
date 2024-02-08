package com.jiltsa.admin.billing.persistence.mapper;

import com.jiltsa.admin.billing.domain.dto.PaymentDto;
import com.jiltsa.admin.billing.persistence.entity.Payment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toPaymentDto(Payment payment);
    List<PaymentDto> toPaymentDtoList(List<Payment> payments);

    @InheritInverseConfiguration
    Payment toPayment(PaymentDto paymentDto);
}
