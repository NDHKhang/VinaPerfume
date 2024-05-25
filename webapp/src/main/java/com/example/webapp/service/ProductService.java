package com.example.webapp.service;

import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Product;

import java.util.List;

public interface ProductService {

    ApiResponse<List<Product>> findAllByAdmin();

    ApiResponse<List<Product>> findAll(String search, int pageNumber);

    ApiResponse<List<Product>> findByCategory(int categoryId, int pageNumber, String search);

    List<Product> findByRelated(int categoryId, int productId, int limit);

    List<Product> findByNew(int limit);

    Product findById(long productId);

    ApiResponse<Product> save(Product product);

    Product deleteById(int productId);

}
