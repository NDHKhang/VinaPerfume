package com.example.webapp.service;

import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    List<Category> findAllByActive();

    Category findById(long categoryId);

    ApiResponse save(Category category);

}
