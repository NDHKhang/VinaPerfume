package com.example.webapp.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Order {

    private long id;
    private String customerId;
    private String customerName;
    private String phone;
    private String address;
    private Date purchaseDate;
    private String dateDisplay;
    private String progress;
    private double totalAmount;
    private String priceDisplay;
    private boolean status;

}
