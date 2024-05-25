package com.example.webapp.service.impl;

import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.service.ImageService;
import com.example.webapp.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private RestTemplate restTemplate;

//    private final String API_URL = "http://localhost:8201/image";
    private final String API_URL = PathUtil.URL_HOST + "/images/image";

    @Override
    public ApiResponse uploadImage(MultipartFile file) {
        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();

            if (file != null && !file.isEmpty()) {
                ByteArrayResource imageResource = new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                };
                multipartRequest.add("image", new HttpEntity<>(imageResource, headers));
            }

            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multipartRequest, headers);

            ResponseEntity<ApiResponse<Map>> responseEntity = restTemplate.exchange(
                    API_URL + "/upload",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<Map> response = responseEntity.getBody();
            return response;
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ApiResponse<Map> apiResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
                    return apiResponse;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

}
