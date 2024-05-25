package com.example.webapp.controller.front;

import com.example.webapp.model.request.OrderDetailRequest;
import com.example.webapp.model.request.OrderRequest;
import com.example.webapp.model.response.*;
import com.example.webapp.service.OrderDetailService;
import com.example.webapp.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("")
    public String viewCheckout(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");

        model.addAttribute("cart", cart);
        return "front/checkout";
    }

    @PostMapping("")
    public String save(Model model, @AuthenticationPrincipal OidcUser oidcUser, HttpSession session, OrderRequest orderRequest) {
        OidcUserInfo oidcUserInfo =  oidcUser.getUserInfo();
        Map<String, Object> claims = oidcUserInfo.getClaims();

        Cart cart = (Cart) session.getAttribute("cart");
        orderRequest.setId(0);
        orderRequest.setProgress("PENDING");
        orderRequest.setStatus(true);
        orderRequest.setPurchaseDate(new Date());
        orderRequest.setCustomerId(claims.getOrDefault("nickname", "").toString());

        ApiResponse<Order> orderResponse = orderService.save(orderRequest);
        if (orderResponse.getStatus() == StatusEnum.ERROR) {
            model.addAttribute("cart", cart);

            return "front/cart";
        }

        List<CartItem> cartItems = cart.getCartItems();
        saveOrderDetail(cartItems, orderResponse.getPayload());

        cart = new Cart();
        cart.setTotalPrice(0);
        cart.setTotalPriceDisplay("0");
        cart.setCartItems(new ArrayList<>());
        session.setAttribute("cart", cart);

        return "redirect:/auth/order";
    }

    private void saveOrderDetail(List<CartItem> cartItems, Order order) {
        for (CartItem cartItem : cartItems) {
            OrderDetailRequest orderDetail = new OrderDetailRequest();
            orderDetail.setOrderId(order.getId());
            orderDetail.setProductId(cartItem.getProduct().getId());
            orderDetail.setProductName(cartItem.getProduct().getName());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setTotalAmount(cartItem.getSubTotal());
            orderDetail.setStatus(true);

            orderDetailService.save(orderDetail);
        }
    }

}
