package com.example.webapp.controller.front;

import com.example.webapp.model.response.Cart;
import com.example.webapp.model.response.CartItem;
import com.example.webapp.model.response.Product;
import com.example.webapp.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String view(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "front/cart";
    }

    @GetMapping("/add-to-cart")
    public String addToCart(Model model, HttpSession session, @RequestParam int productId) {
        Product product = productService.findById(productId);
        Cart cart = (Cart) session.getAttribute("cart");
        cart.addProduct(product, 1);

        model.addAttribute("cart", cart);
        return "front/cart";
    }

    @GetMapping("/desc-to-cart")
    public String descToCart(Model model, HttpSession session, @RequestParam int productId) {
        Product product = productService.findById(productId);
        Cart cart = (Cart) session.getAttribute("cart");
        cart.addProduct(product, - 1);
        model.addAttribute("cart", cart);
        return "front/cart";
    }

    @GetMapping("/remove")
    public String removeCartItem(Model model, HttpSession session, @RequestParam int productId) {
        Product product = productService.findById(productId);
        Cart cart = (Cart) session.getAttribute("cart");
        cart.removeItem(product);
        model.addAttribute("cart", cart);
        return "front/cart";
    }

    @PostMapping("")
    public String submitProduct(Model model, HttpSession session, @RequestParam Long productId,
                                @RequestParam int quantity) {
        Product product = productService.findById(productId);
        Cart cart = (Cart) session.getAttribute("cart");
        cart.addProduct(product, quantity);
        model.addAttribute("cart", cart);
        return "front/cart";
    }

}
