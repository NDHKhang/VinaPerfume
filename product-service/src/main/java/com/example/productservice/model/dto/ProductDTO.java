package com.example.productservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ProductDTO implements Serializable {

    // product
    private Long id;
    private String name;
    private String image;
    private String detail;
    private String description;
    private Double price;
    private String priceDisplay;
    private Boolean status;

    // category
    private Long categoryId;
    private CategoryDTO category;

}

