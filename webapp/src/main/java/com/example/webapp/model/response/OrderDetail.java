package com.example.webapp.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetail {

    private long id;
    private long productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalAmount;
    private String priceDisplay;
    private String totalAmountDisplay;
    private boolean status;

    private String orderId;
    private Order order;

}
