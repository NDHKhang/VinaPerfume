package com.example.webapp.service;

import com.example.webapp.model.request.OrderRequest;
import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(long id);

    List<Order> findByCustomerId(String customerId);

    ApiResponse<Order> save(OrderRequest order);

}
