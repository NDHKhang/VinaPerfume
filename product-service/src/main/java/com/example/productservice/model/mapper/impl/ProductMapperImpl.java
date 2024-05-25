package com.example.productservice.model.mapper.impl;

import com.example.productservice.model.dto.CategoryDTO;
import com.example.productservice.model.dto.ProductDTO;
import com.example.productservice.model.entity.Category;
import com.example.productservice.model.entity.Product;
import com.example.productservice.model.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private DecimalFormat decimalFormat;

    @Override
    public ProductDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( product.getId() );
        productDTO.setName( product.getName() );
        productDTO.setImage( product.getImage() );
        productDTO.setDescription( product.getDescription() );
        productDTO.setDetail( product.getDetail() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setPriceDisplay( decimalFormat.format(product.getPrice()) );
        productDTO.setStatus( product.isStatus() );
        productDTO.setCategoryId( productCategoryId( product ) );
        productDTO.setCategory( categoryToCategoryDTO( product.getCategory() ) );

        return productDTO;
    }

    @Override
    public List<ProductDTO> toListDTO(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( products.size() );
        for ( Product product : products ) {
            list.add( toDTO( product ) );
        }

        return list;
    }

    @Override
    public Product toEntity(Product product, ProductDTO request) {
        if ( request == null || product == null) {
            return null;
        }

        if ( request.getId() != 0 ) {
            product.setId( request.getId() );
        }

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setDetail( request.getDetail() );

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            product.setImage( request.getImage() );
        }

        if ( request.getPrice() != null ) {
            product.setPrice( request.getPrice() );
        }

        if ( request.getStatus() != null ) {
            product.setStatus( request.getStatus() );
        }

        return product;
    }

    private Long productCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        long id = category.getId();
        return id;
    }

    protected CategoryDTO categoryToCategoryDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( category.getId() );
        categoryDTO.setName( category.getName() );
        categoryDTO.setDescription( category.getDescription() );
        categoryDTO.setStatus( category.isStatus() );

        return categoryDTO;
    }

    protected Category categoryDTOToCategory(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        if ( categoryDTO.getId() != null ) {
            category.setId( categoryDTO.getId() );
        }
        category.setName( categoryDTO.getName() );
        category.setDescription( categoryDTO.getDescription() );
        if ( categoryDTO.getStatus() != null ) {
            category.setStatus( categoryDTO.getStatus() );
        }

        return category;
    }
}