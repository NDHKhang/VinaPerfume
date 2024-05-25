package com.example.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.text.NumberFormat;
import java.util.Locale;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public NumberFormat currencyFormat() {
            Locale vietnamLocale = new Locale("vi", "VN");
        return NumberFormat.getCurrencyInstance(vietnamLocale);
    }

}
