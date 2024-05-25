package com.example.webapp.controller.front;

import com.example.webapp.model.response.Product;
import com.example.webapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class DetailController {

    private static final int LIMIT_PRODUCT_RELATED = 6;

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable int id) {
        Product productResponse = productService.findById(id);

        List<Product> productRelated = productService.findByRelated(productResponse.getCategoryId(), id, LIMIT_PRODUCT_RELATED);

        model.addAttribute("product", productResponse);
        model.addAttribute("productRelated", productRelated);
        return "front/detail";
    }

}
