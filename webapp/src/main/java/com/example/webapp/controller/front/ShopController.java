package com.example.webapp.controller.front;

import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Category;
import com.example.webapp.model.response.Product;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop")
public class ShopController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showAll(Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                          @RequestParam(value = "categoryId", required = false, defaultValue = "0") String categoryId,
                          @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        List<Category> categoryList = categoryService.findAllByActive();
        ApiResponse<List<Product>> apiResponse = null;

        if (categoryId == null || categoryId.equalsIgnoreCase("0")) {
            apiResponse = productService.findAll(search, pageNumber);
        } else {
            apiResponse = productService.findByCategory(Integer.parseInt(categoryId), pageNumber, search);
        }

        List<Product> productList = apiResponse.getPayload();
        Map<String, Object> metadata = apiResponse.getMetadata();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productList", productList);
        model.addAttribute("totalPages", metadata.getOrDefault("totalPages", 0));
        model.addAttribute("totalElements", metadata.getOrDefault("totalElements", 0));
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("search", search);
        model.addAttribute("categoryId", categoryId);

        return "front/shop";
    }

}
