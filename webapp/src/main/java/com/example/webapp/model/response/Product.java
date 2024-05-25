package com.example.webapp.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Setter
@Getter
public class Product implements Serializable {

    // product
    private int id;
    private String name;
    private String image;
    private String gallery;
    private String detail;
    private String description;
    private double price;
    private String priceDisplay;
    private boolean status;

    // category
    private int categoryId;
    private Category category;

    // image
    private MultipartFile imageFile;
    private MultipartFile[] galleryFile;

}
