package com.example.orderservice.service;

import com.example.orderservice.model.entity.Order;
import com.example.orderservice.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> findAll();

    OrderDetail findById(long id);

    List<OrderDetail> findByOrder(Order order);

    OrderDetail save(OrderDetail orderDetail);

}
