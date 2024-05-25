package com.example.orderservice.model.dto;

import com.example.orderservice.model.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {

    private long id;
    private long productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalAmount;
    private String priceDisplay;
    private String totalAmountDisplay;
    private boolean status;

    private long orderId;

}
