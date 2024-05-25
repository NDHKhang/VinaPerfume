package com.example.webapp.controller.back;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/back/dashboard")
public class DashboardController {

//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private CategoryService categoryService;

    @GetMapping(value = {"", "/"})
    public String view(Model model) {
//        List<Account> accounts = accountService.findByRole("USER");
//        List<Product> productList = productService.findAll();
//        List<Category> categoryList = categoryService.findAll();
//
//        model.addAttribute("accountSize", accounts.size());
//        model.addAttribute("productSize", productList.size());
//        model.addAttribute("categorySize", categoryList.size());
        return "back/dashboard";
    }

}
