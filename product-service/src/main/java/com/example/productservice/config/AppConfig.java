package com.example.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;

@Configuration
public class AppConfig {

    @Bean
    public DecimalFormat decimalFormat() {
        return new DecimalFormat("###,###");
    }

}
