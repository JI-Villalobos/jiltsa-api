package com.jiltsa.admin.billing.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.billing.domain.dto.PaymentDto;
import com.jiltsa.admin.billing.domain.service.PaymentService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;
    @Operation(summary = "List all payments")
    @GetMapping("/all")
    public List<PaymentDto> getAllPayments(){
        return service.getPayments();
    }

    @Operation(summary = "List payments made after a date", description = "Oldest first.")
    @GetMapping("/since/{date}")
    public List<PaymentDto> getPaymentsSince(@Parameter(description = "Payments made after this instant", example = "2026-03-01T00:00:00") @PathVariable("date")LocalDateTime date){
        return service.getPaymentsSince(date);
    }

    @Operation(summary = "Get a payment by its ticket")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No payment with that ticket")
    @GetMapping("/uuid/{uuid}")
    public PaymentDto getPaymentByTicket(@Parameter(description = "Payment ticket") @PathVariable("uuid") String uuid){
        return service.getPaymentByTicket(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", uuid));
    }

    @Operation(summary = "Get a payment")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No payment with that id")
    @GetMapping("/{id}")
    public PaymentDto getPayment(@Parameter(description = "Payment id") @PathVariable("id") Integer id){
        return service.getPayment(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }

    @Operation(summary = "Register a payment")
    @PostMapping
    public PaymentDto createPayment(@Valid @RequestBody PaymentDto paymentDto){
        return service.createPayment(paymentDto);
    }

    @Operation(summary = "Generate a payment ticket", description = "Returns a new random UUID to identify a payment and the bills it settles.")
    @GetMapping("/ticket")
    public String getTicket(){
        return service.generatePaymentTicket();
    }
}
