package com.jiltsa.admin.billing.domain.repository;

import com.jiltsa.admin.billing.domain.dto.PaymentDto;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public interface PaymentDRepository {
    List<PaymentDto> getPayments();
    List<PaymentDto> getPaymentsSince(LocalDateTime date);
    PaymentDto getPayment(Integer id);
    PaymentDto createPayment(PaymentDto paymentDto);
    String generateTicket();
}
