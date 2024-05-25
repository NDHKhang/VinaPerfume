package com.example.orderservice.model.mapper;

import com.example.orderservice.model.dto.OrderDetailDTO;
import com.example.orderservice.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailMapper {

    OrderDetailDTO toDTO(OrderDetail orderDetail);

    List<OrderDetailDTO> toListDTO(List<OrderDetail> orderDetails);

    OrderDetail toEntity(OrderDetailDTO orderDetailDTO);
    
}
