package com.example.productservice.controller;

import com.example.productservice.model.dto.CategoryDTO;
import com.example.productservice.model.dto.ResponseDTO;
import com.example.productservice.model.dto.StatusEnum;
import com.example.productservice.model.entity.Category;
import com.example.productservice.model.mapper.CategoryMapper;
import com.example.productservice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseDTO> getAllCategories(@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
             @RequestParam(required = false, defaultValue = "9") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Category> categoryPage = categoryService.getAllCategories(pageable);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("totalPages", categoryPage.getTotalPages());
        metadata.put("totalElements", categoryPage.getTotalElements());
        metadata.put("pageNumber", pageNumber);

        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(categoryMapper.toDTOList(categoryPage.getContent()));
        response.setMetadata(metadata);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/list/active")
    public ResponseEntity<ResponseDTO> getAllCategoriesByActive(@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
             @RequestParam(required = false, defaultValue = "9") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Category> categoryPage = categoryService.getAllCategoriesByActive(pageable);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("totalPages", categoryPage.getTotalPages());
        metadata.put("totalElements", categoryPage.getTotalElements());
        metadata.put("pageNumber", pageNumber);

        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(categoryMapper.toDTOList(categoryPage.getContent()));
        response.setMetadata(metadata);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable int id) {
        Category category = categoryService.getById(id);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(categoryMapper.toDTO(category));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult bindingResult) {
//        categoryValidator.validate(categoryDTO, bindingResult);
//            if (bindingResult.hasErrors()) {
//                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
//                        "Vui lòng kiểm tra lại thông tin!"));
//                model.addAttribute("categoryDTO", categoryDTO);
//                return "back/category_form";
//            }

//            if (!ValidatorUtil.isFileEmpty(categoryDTO.getImageMul())) {
//                Map data = uploadFileService.upload(categoryDTO.getImageMul());
//                categoryDTO.setImage((String) data.get("url"));
//            }

        Category category = categoryService.save(categoryDTO);

        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(categoryMapper.toDTO(category));

        return ResponseEntity.ok(response);
    }

}
