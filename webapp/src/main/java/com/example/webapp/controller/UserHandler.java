package com.example.webapp.controller;

import com.example.webapp.model.response.Account;
import com.example.webapp.model.response.Cart;
import com.example.webapp.model.response.CartItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice(annotations = Controller.class)
public class UserHandler {

    private static final DecimalFormat df = new DecimalFormat("###,###,###");

    @Autowired
    private NumberFormat currencyFormat;

    @ModelAttribute("userLogin")
    public Account getCurrentUser(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            return null;
        }

        Account account = new Account();

        // Extract username
        OidcUserInfo oidcUserInfo =  oidcUser.getUserInfo();
        if (oidcUserInfo != null) {
            Map<String, Object> claims = oidcUserInfo.getClaims();
            account.setId(claims.getOrDefault("sub", "").toString());
            account.setEmail(claims.getOrDefault("email", "").toString());
            account.setUsername(claims.getOrDefault("nickname", "").toString());
        }

        return account;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("currencyFormat")
    public NumberFormat getCurrencyFormat() {
        return currencyFormat;
    }

    @ModelAttribute("cart")
    public Cart getCartSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            cart.setTotalPrice(0);
            cart.setTotalPriceDisplay("0");
            List<CartItem> cartItems = new ArrayList<>();
            cart.setCartItems(cartItems);
            session.setAttribute("cart", cart);
        }

        return cart;
    }

}
