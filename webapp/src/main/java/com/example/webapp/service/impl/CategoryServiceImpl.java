package com.example.webapp.service.impl;

import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Category;
import com.example.webapp.model.response.StatusEnum;
import com.example.webapp.service.CategoryService;
import com.example.webapp.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private RestTemplate restTemplate;

//    private final String API_URL = "http://localhost:8001/category";
    private final String API_URL = PathUtil.URL_HOST + "/products/category";

    @Override
    public List<Category> findAll() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/list");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ApiResponse<List<Category>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Category>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Category> findAllByActive() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/list/active");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ApiResponse<List<Category>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Category>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Category findById(long categoryId) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", String.valueOf(categoryId));

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/id/" + categoryId);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ApiResponse<Category>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<Category> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ApiResponse save(Category category) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity<>(category, headers);

            ResponseEntity<ApiResponse<Category>> responseEntity = restTemplate.exchange(
                    API_URL + "/submit",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<Category> response = responseEntity.getBody();
            return response;
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ApiResponse apiResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
                    return apiResponse;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

}
