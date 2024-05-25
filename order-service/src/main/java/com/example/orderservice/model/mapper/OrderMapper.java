package com.example.orderservice.model.mapper;

import com.example.orderservice.model.dto.OrderDTO;
import com.example.orderservice.model.entity.Order;

import java.util.List;

public interface OrderMapper {

    OrderDTO toDTO(Order order);

    List<OrderDTO> toListDTO(List<Order> orders);

    Order toEntity(OrderDTO orderDTO);

}
