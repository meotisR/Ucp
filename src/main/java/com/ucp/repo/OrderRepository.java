package com.ucp.repo;

import com.ucp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCompositionId(Integer compositionId);
    List<Order> findAllByIsActiveTrue();
}