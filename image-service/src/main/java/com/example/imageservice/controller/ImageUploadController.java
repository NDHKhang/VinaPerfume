package com.example.imageservice.controller;

import com.example.imageservice.model.ResponseDTO;
import com.example.imageservice.model.StatusEnum;
import com.example.imageservice.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageUploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> uploadImage(@RequestParam("image") MultipartFile file){
        Map data = cloudinaryService.upload(file);

        ResponseDTO response = new ResponseDTO();
        response.setStatus(StatusEnum.SUCCESS);
        response.setPayload(data);

        return ResponseEntity.ok(response);
    }

}
