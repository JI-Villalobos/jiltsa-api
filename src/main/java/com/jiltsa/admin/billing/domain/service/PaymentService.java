package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.PaymentDto;
import com.jiltsa.admin.billing.persistence.entity.Payment;
import com.jiltsa.admin.billing.persistence.mapper.PaymentMapper;
import com.jiltsa.admin.billing.persistence.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public List<PaymentDto> getPayments() {
        return mapper.toPaymentDtoList(repository.findAll());
    }

    public List<PaymentDto> getPaymentsSince(LocalDateTime date) {
        return mapper.toPaymentDtoList(repository.findByDateAfterOrderByDateAsc(date));
    }

    public Optional<PaymentDto> getPayment(Integer id) {
        return repository.findById(id).map(mapper::toPaymentDto);
    }

    public Optional<PaymentDto> getPaymentByTicket(String ticket) {
        return repository.findByTicket(ticket).map(mapper::toPaymentDto);
    }

    @Transactional
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = mapper.toPayment(paymentDto);
        return mapper.toPaymentDto(repository.save(payment));
    }

    public String generatePaymentTicket() {
        return UUID.randomUUID().toString();
    }
}
