package com.example.orderservice.controller;

import com.example.orderservice.model.dto.OrderDTO;
import com.example.orderservice.model.dto.ResponseDTO;
import com.example.orderservice.model.dto.StatusEnum;
import com.example.orderservice.model.entity.Order;
import com.example.orderservice.model.mapper.OrderMapper;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> getByAll() {
        List<Order> orderList = orderService.findAll();
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(orderMapper.toListDTO(orderList));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderDTO>> getById(@PathVariable long id) {
        Order order = orderService.findById(id);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(orderMapper.toDTO(order));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> getByCustomer(@PathVariable String customerId) {
        List<Order> orderList = orderService.findByCustomerId(customerId);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(orderMapper.toListDTO(orderList));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<ResponseDTO<OrderDTO>> save(@RequestBody @Valid OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        orderService.save(order);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(orderMapper.toDTO(order));

        return ResponseEntity.ok(response);
    }

}
