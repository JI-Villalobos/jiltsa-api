package com.jiltsa.admin.billing.domain.repository;

import com.jiltsa.admin.billing.domain.dto.PaymentDto;

import java.time.Month;
import java.util.List;

public interface PaymentDRepository {
    List<PaymentDto> getPayments();
    List<PaymentDto> getPaymentsByMonth(Month month);
    PaymentDto createPayment(PaymentDto paymentDto);
    String generateTicket();
}
