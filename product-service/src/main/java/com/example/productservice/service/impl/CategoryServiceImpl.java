package com.example.productservice.service.impl;

import com.example.productservice.model.dto.CategoryDTO;
import com.example.productservice.model.entity.Category;
import com.example.productservice.model.mapper.CategoryMapper;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> getAllCategoriesByActive(Pageable pageable) {
        return categoryRepository.findByStatusIsTrue(pageable);
    }

    @Override
    public Category getById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(CategoryDTO categoryDTO) {
        Category category = null;
        if (categoryDTO.getId() == null || categoryDTO.getId() == 0) {
            category = new Category();
        } else {
            category = categoryRepository.findById(categoryDTO.getId()).orElse(null);
        }

        // mapper
        categoryMapper.toEntity(category, categoryDTO);

        // save
        return categoryRepository.save(category);
    }

}
