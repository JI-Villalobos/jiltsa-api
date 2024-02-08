package com.jiltsa.admin.billing.persistence.mapper;

import com.jiltsa.admin.billing.domain.dto.PaymentDto;
import com.jiltsa.admin.billing.persistence.entity.Payment;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {

    @Test
    void shouldMapToPaymentDto() {
        //given
        PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);
        Payment payment = new Payment(1, LocalDateTime.now(), 345.00, UUID.randomUUID().toString());

        //when
        PaymentDto paymentDto = mapper.toPaymentDto(payment);

        //then
        assertThat(paymentDto).isNotNull();
        assertThat(paymentDto).isInstanceOf(PaymentDto.class);
    }

    @Test
    void shouldMapToPaymentDtoList() {
        PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);
        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment(1, LocalDateTime.now(), 345.00, UUID.randomUUID().toString());
        Payment payment2 = new Payment(2, LocalDateTime.now(), 345.00, UUID.randomUUID().toString());
        payments.add(payment);
        payments.add(payment2);

        //when
        List<PaymentDto> paymentDtoList = mapper.toPaymentDtoList(payments);

        //then
        assertThat(paymentDtoList).isNotNull();
        assertThat(paymentDtoList.size()).isEqualTo(2);
    }

    @Test
    void shouldMapToPayment() {
        //given
        PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);
        PaymentDto paymentDto = new PaymentDto(LocalDateTime.now(), 345.00, UUID.randomUUID().toString());

        //when
        Payment payment = mapper.toPayment(paymentDto);

        //then
        assertThat(payment).isInstanceOf(Payment.class);
        assertThat(payment.getAmount()).isEqualTo(345.00);
    }
}