package com.example.webapp.service.impl;

import com.example.webapp.model.request.OrderRequest;
import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Order;
import com.example.webapp.model.response.StatusEnum;
import com.example.webapp.service.OrderService;
import com.example.webapp.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

//    private final String API_URL = "http://localhost:8101/order";
    private final String API_URL = PathUtil.URL_HOST + "/orders/order";

    @Override
    public List<Order> findAll() {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL);
            ResponseEntity<ApiResponse<List<Order>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Order>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Order findById(long id) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/" + id);
            ResponseEntity<ApiResponse<Order>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<Order> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Order> findByCustomerId(String customerId) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/customer/" + customerId);
            ResponseEntity<ApiResponse<List<Order>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<Order>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ApiResponse<Order> save(OrderRequest order) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity<>(order, headers);

            ResponseEntity<ApiResponse<Order>> responseEntity = restTemplate.exchange(
                    API_URL + "/submit",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

}
