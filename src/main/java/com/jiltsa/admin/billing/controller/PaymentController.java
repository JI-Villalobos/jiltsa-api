package com.jiltsa.admin.billing.controller;

import com.jiltsa.admin.billing.domain.dto.PaymentDto;
import com.jiltsa.admin.billing.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;
    @GetMapping("/all")
    public List<PaymentDto> getAllPayments(){
        return service.getPayments();
    }

    @GetMapping("/{date}")
    public List<PaymentDto> getPaymentsSince(@PathVariable("date")LocalDateTime date){
        return service.getPaymentsSince(date);
    }

    @GetMapping("/{id}")
    public Optional<PaymentDto> getPayment(@PathVariable("id") Integer id){
        return service.getPayment(id);
    }

    @PostMapping
    public PaymentDto createPayment(@RequestBody PaymentDto paymentDto){
        return service.createPayment(paymentDto);
    }

    @GetMapping("/ticket")
    public String getTicket(){
        return service.generatePaymentTicket();
    }
}
