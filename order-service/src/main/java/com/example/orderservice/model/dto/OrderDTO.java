package com.example.orderservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private long id;
    private String customerId;
    private String customerName;
    private String phone;
    private String address;
    private Date purchaseDate;
    private String dateDisplay;
    private String progress;
    private String priceDisplay;
    private double totalAmount;
    private boolean status;

    List<OrderDetailDTO> orderDetailDTOList;

}
