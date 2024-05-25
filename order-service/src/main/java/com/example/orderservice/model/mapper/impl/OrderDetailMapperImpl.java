package com.example.orderservice.model.mapper.impl;

import com.example.orderservice.model.dto.OrderDetailDTO;
import com.example.orderservice.model.entity.OrderDetail;
import com.example.orderservice.model.mapper.OrderDetailMapper;
import com.example.orderservice.service.OrderDetailService;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Autowired
    private DecimalFormat decimalFormat;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        if (orderDetail == null){
            return null;
        }
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setProductId(orderDetail.getProductId());
        orderDetailDTO.setProductName(orderDetail.getProductName());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setTotalAmount(orderDetail.getTotalAmount());
        orderDetailDTO.setPriceDisplay(decimalFormat.format(orderDetail.getPrice()));
        orderDetailDTO.setTotalAmountDisplay(decimalFormat.format(orderDetail.getTotalAmount()));
        orderDetailDTO.setStatus(orderDetail.isStatus());

        orderDetailDTO.setOrderId(orderDetail.getOrder().getOrderId());
        return orderDetailDTO;
    }

    @Override
    public List<OrderDetailDTO> toListDTO(List<OrderDetail> orderDetails) {
        if (orderDetails == null) {
            return null;
        }

        List<OrderDetailDTO> result = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail != null) {
                result.add(toDTO(orderDetail));
            }
        }

        return result;
    }

    @Override
    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        if (orderDetailDTO == null) {
            return null;
        }
        OrderDetail orderDetail = orderDetailService.findById(orderDetailDTO.getId());
        if (orderDetail == null) {
            orderDetail = new OrderDetail();
        }
        orderDetail.setProductId(orderDetailDTO.getProductId());
        orderDetail.setProductName(orderDetailDTO.getProductName());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setTotalAmount(orderDetailDTO.getTotalAmount());
        orderDetail.setStatus(orderDetailDTO.isStatus());

        orderDetail.setOrder(orderService.findById(orderDetailDTO.getOrderId()));

        return orderDetail;
    }

}
