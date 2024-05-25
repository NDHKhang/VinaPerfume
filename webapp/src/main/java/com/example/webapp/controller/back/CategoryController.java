package com.example.webapp.controller.back;

import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Category;
import com.example.webapp.model.response.StatusEnum;
import com.example.webapp.service.CategoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/category")
public class CategoryController {

    private static final String REDIRECT_URL = "/back/category";

    @Autowired
    private CategoryService categoryService;

//    @Autowired
//    private CategoryMapper categoryMapper;
//
//    @Autowired
//    private UploadFileService uploadFileService;
//
//    @Autowired
//    private CategoryValidator categoryValidator;

    @GetMapping(value = {""})
    public String list(Model model) {
        try {
            List<Category> categoryList = categoryService.findAll();
            model.addAttribute("categoryList", categoryList);
            return "back/category_list";
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String create(Model model) {
        try {
            Category category = new Category();
            model.addAttribute("categoryDTO", category);
            return "back/category_form";
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form/{id}")
    public String edit(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Category category = categoryService.findById(id);
            if (category == null) {
                category = new Category();
            }

            model.addAttribute("categoryDTO", category);

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

            return "back/category_form";
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form")
    public String save(Model model, Category category) {
        try {
            ApiResponse<Category> apiResponse = categoryService.save(category);
            if (apiResponse.getStatus() == StatusEnum.ERROR) {
                model.addAttribute("categoryDTO", category);

                return "back/category_form";
            }

            String redirectUrl = "/back/category/form/" + apiResponse.getPayload().getId() + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
