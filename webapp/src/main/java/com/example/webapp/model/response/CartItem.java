package com.example.webapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Product product;
    private int quantity;
    private double subTotal;
    private String subTotalDisplay;

}
