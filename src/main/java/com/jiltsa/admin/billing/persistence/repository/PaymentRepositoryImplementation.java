package com.jiltsa.admin.billing.persistence.repository;

import com.jiltsa.admin.billing.domain.dto.PaymentDto;
import com.jiltsa.admin.billing.domain.repository.PaymentDRepository;
import com.jiltsa.admin.billing.persistence.entity.Payment;
import com.jiltsa.admin.billing.persistence.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImplementation implements PaymentDRepository {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    @Override
    public List<PaymentDto> getPayments() {
        return mapper.toPaymentDtoList(repository.findAll());
    }

    @Override
    public List<PaymentDto> getPaymentsSince(LocalDateTime date) {
        return mapper.toPaymentDtoList(repository.findByDateAfterOrderByDateAsc(date));
    }

    @Override
    public Optional<PaymentDto> getPayment(Integer id) {
        return repository.findById(id).map(mapper::toPaymentDto);
    }

    @Override
    public Optional<PaymentDto> getPaymentByTicket(String ticket) {
        return repository.findByTicket(ticket).map(mapper::toPaymentDto);
    }

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = mapper.toPayment(paymentDto);
        return mapper.toPaymentDto(repository.save(payment));
    }

    @Override
    public String generateTicket() {
        return UUID.randomUUID().toString();
    }
}
