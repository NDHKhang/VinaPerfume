package com.example.productservice.service;

import com.example.productservice.model.dto.CategoryDTO;
import com.example.productservice.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<Category> getAllCategories(Pageable pageable);

    Page<Category> getAllCategoriesByActive(Pageable pageable);

    Category getById(long id);

    Category save(CategoryDTO categoryDTO);

}
