package com.jiltsa.admin.orders.persistence.repository;

import com.jiltsa.admin.orders.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByIsOpenTrue();
}
