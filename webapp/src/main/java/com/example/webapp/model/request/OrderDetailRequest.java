package com.example.webapp.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderDetailRequest implements Serializable {

    private long productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalAmount;
    private boolean status;
    private long orderId;

}
