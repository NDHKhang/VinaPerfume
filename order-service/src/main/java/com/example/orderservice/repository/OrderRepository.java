package com.example.orderservice.repository;

import com.example.orderservice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByProgress(String progress);

    List<Order> findByCustomerId(String id);

}
