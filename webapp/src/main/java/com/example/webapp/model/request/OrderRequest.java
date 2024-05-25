package com.example.webapp.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class OrderRequest implements Serializable {

    // order
    private long id;
    private String customerId;
    private String customerName;
    private String phone;
    private String address;
    private Date purchaseDate;
    private String progress;
    private double totalAmount;
    private boolean status;

}
