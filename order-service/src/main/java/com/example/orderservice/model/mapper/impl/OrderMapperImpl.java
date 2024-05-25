package com.example.orderservice.model.mapper.impl;

import com.example.orderservice.model.dto.OrderDTO;
import com.example.orderservice.model.entity.Order;
import com.example.orderservice.model.entity.OrderDetail;
import com.example.orderservice.model.mapper.OrderDetailMapper;
import com.example.orderservice.model.mapper.OrderMapper;
import com.example.orderservice.service.OrderDetailService;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderMapperImpl implements OrderMapper {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    @Autowired
    private DecimalFormat decimalFormat;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getOrderId());
        orderDTO.setPhone(order.getPhone());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setPurchaseDate(order.getPurchaseDate());
        orderDTO.setDateDisplay(convertDateToString(order.getPurchaseDate()));
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setPriceDisplay(decimalFormat.format(order.getTotalAmount()));
        orderDTO.setProgress(order.getProgress());
        orderDTO.setStatus(order.isStatus());

        orderDTO.setCustomerId(order.getCustomerId());
        orderDTO.setCustomerName(order.getCustomerName());

        List<OrderDetail> orderDetailList = orderDetailService.findByOrder(order);
        orderDTO.setOrderDetailDTOList(orderDetailMapper.toListDTO(orderDetailList));

        return orderDTO;
    }

    @Override
    public List<OrderDTO> toListDTO(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        List<OrderDTO> result = new ArrayList<>();
        for (Order order : orders) {
            if (order != null) {
                result.add(toDTO(order));
            }
        }

        return result;
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        Order order = orderService.findById(orderDTO.getId());
        if (order == null) {
            order = new Order();
        }

        order.setAddress(orderDTO.getAddress());
        order.setPhone(orderDTO.getPhone());
        order.setProgress(orderDTO.getProgress());
        if (orderDTO.getPurchaseDate() != null) {
            order.setPurchaseDate(orderDTO.getPurchaseDate());
        }
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.isStatus());
        order.setCustomerId(orderDTO.getCustomerId());
        order.setCustomerName(orderDTO.getCustomerName());

        return order;
    }

    private String convertDateToString(Date value) {
        return new SimpleDateFormat(DATE_PATTERN).format(value);
    }

}
