package com.example.webapp.service;

import com.example.webapp.model.request.OrderDetailRequest;
import com.example.webapp.model.response.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> findAll();

    List<OrderDetail> findByOrder(String orderId);

    OrderDetail save(OrderDetailRequest orderDetail);

}
