package com.example.webapp.controller.front;

import com.example.webapp.model.response.Product;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = {"", "/" , "/home"})
public class HomeController {

    private static final int LIMIT_NEW_PRODUCT = 8;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showHome(Model model) {
        List<Product> productList = productService.findByNew(LIMIT_NEW_PRODUCT);

        model.addAttribute("productList", productList);
        return "front/home";
    }

}
