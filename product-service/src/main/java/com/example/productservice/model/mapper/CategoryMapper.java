package com.example.productservice.model.mapper;

import com.example.productservice.model.dto.CategoryDTO;
import com.example.productservice.model.entity.Category;

import java.util.List;

public interface CategoryMapper {

    // Map Entity to DTO
    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTOList(List<Category> categoryList);

    // Map DTO to Entity
    Category toEntity(Category category, CategoryDTO categoryDTO);

}
