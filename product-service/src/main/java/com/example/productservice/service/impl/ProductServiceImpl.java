package com.example.productservice.service.impl;

import com.example.productservice.model.dto.ProductDTO;
import com.example.productservice.model.entity.Category;
import com.example.productservice.model.entity.Product;
import com.example.productservice.model.mapper.ProductMapper;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.CategoryService;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findProductsByActive(String search, Pageable pageable) {
        return productRepository.findByNameContainingAndStatusIsTrue(search, pageable);
    }

    @Override
    public Page<Product> findProductsByCategory(Category category, String search, Pageable pageable) {
        return productRepository.findByCategoryAndNameContainingAndStatusIsTrue(category, search, pageable);
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findByRelated(int categoryId, int productId, int limit) {
        return productRepository.findByRelated(categoryId, productId, limit);
    }

    @Override
    public List<Product> findByNew(int limit) {
        return productRepository.findByNew(limit);
    }

    @Override
    public Product save(ProductDTO productDTO) {
        Product product = null;
        if (productDTO.getId() == null || productDTO.getId() == 0) {
            product = new Product();
        } else {
            product = productRepository.findById(productDTO.getId()).orElse(null);
        }

        // mapper
        productMapper.toEntity(product, productDTO);

        // category
        Category category = categoryService.getById(productDTO.getCategoryId());
        product.setCategory(category);

        // save
        return productRepository.save(product);
    }
}
