package com.example.webapp.service.impl;

import com.example.webapp.model.request.OrderDetailRequest;
import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.OrderDetail;
import com.example.webapp.model.response.StatusEnum;
import com.example.webapp.service.OrderDetailService;
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
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private RestTemplate restTemplate;

//    private final String API_URL = "http://localhost:8101/order-detail";
    private final String API_URL = PathUtil.URL_HOST + "/orders/order-detail";

    @Override
    public List<OrderDetail> findAll() {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL);
            ResponseEntity<ApiResponse<List<OrderDetail>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<OrderDetail>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<OrderDetail> findByOrder(String orderId) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("orderId", orderId);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ApiResponse<List<OrderDetail>>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<List<OrderDetail>> apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getStatus().equals(StatusEnum.SUCCESS)) {
                return apiResponse.getPayload();
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public OrderDetail save(OrderDetailRequest orderDetail) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity<>(orderDetail, headers);

            ResponseEntity<ApiResponse<OrderDetail>> responseEntity = restTemplate.exchange(
                    API_URL + "/submit",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            ApiResponse<OrderDetail> response = responseEntity.getBody();
            return response.getPayload();
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ApiResponse<OrderDetail> apiResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
                    return apiResponse.getPayload();
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }
}
