package com.jiltsa.admin.billing.persistence.repository;

import com.jiltsa.admin.billing.persistence.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
