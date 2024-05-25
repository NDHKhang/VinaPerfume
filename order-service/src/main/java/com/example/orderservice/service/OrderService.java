package com.example.orderservice.service;

import com.example.orderservice.model.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(long id);

    List<Order> findByCustomerId(String id);

    Order save(Order order);

}
