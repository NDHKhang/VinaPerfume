package com.example.webapp.model.response;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {

    private List<CartItem> cartItems;
    private double totalPrice;
    private String totalPriceDisplay;

    public void addProduct(Product product, int quantity) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        double subTotal;
        String subTotalDisplay;

        if (quantity > 0) {
            boolean isCart = true;
            for (CartItem item : cartItems) {
                if (item.getProduct().getId() == product.getId()) {
                    item.setQuantity(item.getQuantity() + quantity);

                    subTotal = item.getQuantity() * item.getProduct().getPrice();
                    subTotalDisplay = new DecimalFormat("###,###").format(subTotal);
                    item.setSubTotal(subTotal);
                    item.setSubTotalDisplay(subTotalDisplay);

                    totalPrice = updateTotalPrice(cartItems);
                    totalPriceDisplay = new DecimalFormat("###,###").format(totalPrice);

                    isCart = false;
                    break;
                }
            }

            if (isCart) {
                subTotal = product.getPrice() * quantity;
                subTotalDisplay = new DecimalFormat("###,###").format(subTotal);
                cartItems.add(new CartItem(product, quantity, subTotal, subTotalDisplay));
                totalPrice = updateTotalPrice(cartItems);
                totalPriceDisplay = new DecimalFormat("###,###").format(totalPrice);
            }
        } else {
            for (CartItem item : cartItems) {
                if (item.getProduct().getId() == product.getId()) {
                    item.setQuantity(item.getQuantity() + quantity);

                    subTotal = item.getQuantity() * item.getProduct().getPrice();
                    subTotalDisplay = new DecimalFormat("###,###").format(subTotal);
                    item.setSubTotal(subTotal);
                    item.setSubTotalDisplay(subTotalDisplay);

                    totalPrice = updateTotalPrice(cartItems);
                    totalPriceDisplay = new DecimalFormat("###,###").format(totalPrice);
                    break;
                }
            }
        }

        // clean data
        cartItems.removeIf(item -> item.getQuantity() == 0);
    }

    public void removeItem(Product product) {
        cartItems.removeIf(item -> item.getProduct().getId() == product.getId());
        totalPrice = updateTotalPrice(cartItems);
        totalPriceDisplay = new DecimalFormat("###,###").format(totalPrice);
    }

    private double updateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getSubTotal();
        }
        return totalPrice;
    }

}
