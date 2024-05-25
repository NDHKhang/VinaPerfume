package com.example.productservice.model.mapper;

import com.example.productservice.model.dto.ProductDTO;
import com.example.productservice.model.entity.Product;

import java.util.List;

public interface ProductMapper {

    ProductDTO toDTO(Product product);

    List<ProductDTO> toListDTO(List<Product> products);

    // Map Request to Entity
    Product toEntity(Product product, ProductDTO request);

}
