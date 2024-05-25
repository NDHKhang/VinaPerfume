package com.example.webapp.controller.back;


import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Category;
import com.example.webapp.model.response.Product;
import com.example.webapp.model.response.StatusEnum;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.ImageService;
import com.example.webapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/back/product")
public class ProductController {

    private static final String REDIRECT_URL = "/back/product";

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @GetMapping(value = {""})
    public String list(Model model) {
        try {
            ApiResponse<List<Product>> apiResponse = productService.findAllByAdmin();
            model.addAttribute("productList", apiResponse.getPayload());
            return "back/product_list";
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String create(Model model) {
        try {
            Product productDTO = new Product();
            List<Category> categoryList = categoryService.findAll();

            model.addAttribute("productDTO", productDTO);
            model.addAttribute("categoryDTOList", categoryList);
            return "back/product_form";
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form/{id}")
    public String edit(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Product product = productService.findById(id);
            List<Category> categoryList = categoryService.findAll();

            model.addAttribute("productDTO", product);
            model.addAttribute("categoryDTOList", categoryList);

            if (action == null) {
                model.addAttribute("messageDTO", null);
            } else {
                model.addAttribute("messageDTO", "messageDTO");
                if (status.equals("success")) {
                    model.addAttribute("status", "success");
                    model.addAttribute("message", "Cập nhật dữ liệu thành công!");
                } else {
                    model.addAttribute("status", "error");
                    model.addAttribute("message", "Vui lòng kiểm tra lại thông tin!");
                }
            }

            return "back/product_form";
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form")
    public String save(Model model, Product product) {
        try {
            // upload image
            if (product.getImageFile() != null && !product.getImageFile().getOriginalFilename().isEmpty()) {
                ApiResponse<Map> responseUpload = imageService.uploadImage(product.getImageFile());

                if (responseUpload.getPayload() != null) {
                    Map data = responseUpload.getPayload();
                    product.setImage(data.getOrDefault("url", null).toString());
                }
            }

            ApiResponse<Product> apiResponse = productService.save(product);
            if (apiResponse == null || apiResponse.getStatus() == StatusEnum.ERROR) {
                List<Category> categoryList = categoryService.findAll();

                model.addAttribute("productDTO", product);
                model.addAttribute("categoryDTOList", categoryList);

                return "back/product_form";
            }

            String redirectUrl = "/back/product/form/" + apiResponse.getPayload().getId() + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
