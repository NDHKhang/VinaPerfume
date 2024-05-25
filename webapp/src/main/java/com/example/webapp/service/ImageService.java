package com.example.webapp.service;

import com.example.webapp.model.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ApiResponse uploadImage(MultipartFile file);

}
