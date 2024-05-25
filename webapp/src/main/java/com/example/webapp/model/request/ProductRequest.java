package com.example.webapp.model.request;

import com.example.webapp.model.response.Category;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ProductRequest implements Serializable {

    // product
    private int id;
    private String name;
    private String image;
    private String gallery;
    private String detail;
    private String description;
    private double price;
    private boolean status;

    // category
    private int categoryId;

}
