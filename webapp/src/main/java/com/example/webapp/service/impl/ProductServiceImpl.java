package com.example.webapp.service.impl;

import com.example.webapp.model.request.ProductRequest;
import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Product;
import com.example.webapp.model.response.StatusEnum;
import com.example.webapp.service.ProductService;
import com.example.webapp.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestTemplate restTemplate;

    // private final String API_URL = "http://localhost:8001/product";
   private final String API_URL = PathUtil.URL_HOST + "/products/product";

    @Override
    public ApiResponse<List<Product>> findAllByAdmin() {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/list");
            ResponseEntity<ApiResponse<List<Product>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Product>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ApiResponse<List<Product>> findAll(String search, int pageNumber) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("search", search);
            params.put("pageNumber", String.valueOf(pageNumber));

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/list/active");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ApiResponse<List<Product>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Product>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ApiResponse<List<Product>> findByCategory(int categoryId, int pageNumber, String search) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("categoryId", String.valueOf(categoryId));
            params.put("pageNumber", String.valueOf(pageNumber));
            params.put("search", search);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/list/active");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            ResponseEntity<ApiResponse<List<Product>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Product>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Product> findByRelated(int categoryId, int productId, int limit) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("categoryId", String.valueOf(categoryId));
            params.put("productId", String.valueOf(productId));
            params.put("limit", String.valueOf(limit));

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/related");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String url = builder.toUriString();
            ResponseEntity<ApiResponse<List<Product>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Product>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Product> findByNew(int limit) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("limit", String.valueOf(limit));

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/new");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String url = builder.toUriString();
            ResponseEntity<ApiResponse<List<Product>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Product>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Product findById(long productId) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/id/" + productId);

            ResponseEntity<ApiResponse<Product>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<Product> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ApiResponse<Product> save(Product product) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ProductRequest productRequest = new ProductRequest();
            productRequest.setId( product.getId() );
            productRequest.setName( product.getName() );
            productRequest.setImage( product.getImage() );
            productRequest.setDescription( product.getDescription() );
            productRequest.setDetail( product.getDetail() );
            productRequest.setPrice( product.getPrice() );
            productRequest.setStatus( product.isStatus() );
            productRequest.setCategoryId( product.getCategoryId() );

            HttpEntity httpEntity = new HttpEntity<>(productRequest, headers);

            ResponseEntity<ApiResponse<Product>> responseEntity = restTemplate.exchange(
                    API_URL + "/submit",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<Product> response = responseEntity.getBody();
            return response;
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ApiResponse<Product> apiResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
                    return apiResponse;
                }
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    @Override
    public Product deleteById(int productId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity<>(productId, headers);

            ResponseEntity<ApiResponse<Product>> responseEntity = restTemplate.exchange(
                    API_URL + "/" + productId,
                    HttpMethod.DELETE,
                    httpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<Product> response = responseEntity.getBody();
            return response.getPayload();
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ApiResponse<Product> apiResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
                    return apiResponse.getPayload();
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

}

