package com.example.productservice.controller;

import com.example.productservice.model.dto.ProductDTO;
import com.example.productservice.model.dto.ResponseDTO;
import com.example.productservice.model.dto.StatusEnum;
import com.example.productservice.model.entity.Category;
import com.example.productservice.model.entity.Product;
import com.example.productservice.model.mapper.ProductMapper;
import com.example.productservice.service.CategoryService;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = {"/list"})
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getAllProducts() {
        List<Product> productPage = productService.findAll();

        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(productMapper.toListDTO(productPage));

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = {"/list/active"})
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getAllProductsByActive(
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "9") Integer pageSize,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(required = false, defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        String keyword = "";
        try {
            keyword = URLDecoder.decode(search, "UTF-8");
        } catch (Exception e) {
            keyword = search;
        }

        Page<Product> productPage = null;
        if (categoryId == null) {
            productPage = productService.findProductsByActive(keyword, pageable);
        } else {
            Category category = categoryService.getById(Integer.parseInt(categoryId));
            productPage = productService.findProductsByCategory(category, keyword, pageable);
        }

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("totalPages", productPage.getTotalPages());
        metadata.put("totalElements", productPage.getTotalElements());
        metadata.put("pageNumber", pageNumber);

        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(productMapper.toListDTO(productPage.getContent()));
        response.setMetadata(metadata);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> getByIdByPathVariable(@PathVariable int id) {
        Product product = productService.findById(id);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(productMapper.toDTO(product));

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/related")
    public ResponseEntity<ResponseDTO<ProductDTO>> getByRelated(@RequestParam String categoryId,
             @RequestParam String productId, @RequestParam String limit) {
        List<Product> productList = productService.findByRelated(Integer.parseInt(categoryId), Integer.parseInt(productId), Integer.parseInt(limit));
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(productMapper.toListDTO(productList));

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/new")
    public ResponseEntity<ResponseDTO<ProductDTO>> getByNew(@RequestParam int limit) {
        List<Product> productList = productService.findByNew(limit);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(productMapper.toListDTO(productList));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<ResponseDTO> save(@RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        Product product = productService.save(productDTO);

        // save product
        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(productMapper.toDTO(product));

        return ResponseEntity.ok(response);
    }

}
