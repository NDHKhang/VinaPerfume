package com.example.orderservice.controller;

import com.example.orderservice.model.dto.OrderDetailDTO;
import com.example.orderservice.model.dto.ResponseDTO;
import com.example.orderservice.model.dto.StatusEnum;
import com.example.orderservice.model.entity.Order;
import com.example.orderservice.model.entity.OrderDetail;
import com.example.orderservice.model.mapper.OrderDetailMapper;
import com.example.orderservice.model.mapper.OrderMapper;
import com.example.orderservice.service.OrderDetailService;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order-detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<OrderDetailDTO>>> getByAll(@RequestParam(required = false) String orderId) {
        List<OrderDetail> orderDetailList;

        if (orderId.equals("0")) {
            orderDetailList = orderDetailService.findAll();
        } else {
            Order order = orderService.findById(Long.parseLong(orderId));
            orderDetailList = orderDetailService.findByOrder(order);
        }

        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(orderDetailMapper.toListDTO(orderDetailList));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderDetailDTO>> getById(@PathVariable long id) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(orderDetailMapper.toDTO(orderDetail));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<ResponseDTO<OrderDetailDTO>> save(@RequestBody @Valid OrderDetailDTO orderDetailDTO, BindingResult bindingResult) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);
        orderDetailService.save(orderDetail);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(orderDetailMapper.toDTO(orderDetail));

        return ResponseEntity.ok(response);
    }

}
