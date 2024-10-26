package com.jiltsa.admin.orders.persistence.repository;

import com.jiltsa.admin.orders.persistence.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
