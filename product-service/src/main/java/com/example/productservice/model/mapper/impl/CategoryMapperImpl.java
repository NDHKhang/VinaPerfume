package com.example.productservice.model.mapper.impl;

import com.example.productservice.model.dto.CategoryDTO;
import com.example.productservice.model.entity.Category;
import com.example.productservice.model.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO toDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setDescription( category.getDescription() );
        categoryDTO.setId( category.getId() );
        categoryDTO.setName( category.getName() );
        categoryDTO.setStatus( category.isStatus() );

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> toDTOList(List<Category> categoryList) {
        if ( categoryList == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( categoryList.size() );
        for ( Category category : categoryList ) {
            list.add( toDTO( category ) );
        }

        return list;
    }

    @Override
    public Category toEntity(Category category, CategoryDTO categoryDTO) {
        if ( categoryDTO == null || category == null) {
            return null;
        }

        if ( categoryDTO.getId() != null ) {
            category.setId( categoryDTO.getId() );
        }
        category.setDescription( categoryDTO.getDescription() );
        category.setName( categoryDTO.getName() );
        if ( categoryDTO.getStatus() != null ) {
            category.setStatus( categoryDTO.getStatus() );
        }

        return category;
    }

}
