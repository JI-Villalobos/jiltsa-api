package com.jiltsa.admin.billing.persistence.repository;

import com.jiltsa.admin.billing.persistence.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByDateAfterOrderByDateAsc(LocalDateTime date);
    Optional<Payment> findByTicket(String ticket);
}
