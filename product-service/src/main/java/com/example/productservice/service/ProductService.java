package com.example.productservice.service;

import com.example.productservice.model.dto.ProductDTO;
import com.example.productservice.model.entity.Category;
import com.example.productservice.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Page<Product> findAllProducts(Pageable pageable);

    Page<Product> findProductsByActive(String search, Pageable pageable);

    Page<Product> findProductsByCategory(Category category, String search, Pageable pageable);

    Product findById(long id);

    List<Product> findByRelated(int categoryId, int productId, int limit);

    List<Product> findByNew(int limit);

    Product save(ProductDTO productDTO);

}
