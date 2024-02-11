package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.PaymentDto;
import com.jiltsa.admin.billing.domain.repository.PaymentDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentDRepository paymentDRepository;
    public List<PaymentDto> getPayments(){
        return paymentDRepository.getPayments();
    }
    public List<PaymentDto> getPaymentsSince(LocalDateTime date){
        return paymentDRepository.getPaymentsSince(date);
    }
    public Optional<PaymentDto> getPayment(Integer id){
        return paymentDRepository.getPayment(id);
    }
    public PaymentDto createPayment(PaymentDto paymentDto){
        return paymentDRepository.createPayment(paymentDto);
    }
    public String generatePaymentTicket(){
        return paymentDRepository.generateTicket();
    }
}
