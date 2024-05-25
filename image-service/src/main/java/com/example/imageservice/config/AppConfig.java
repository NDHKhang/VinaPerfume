package com.example.imageservice.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap();
        config.put("cloud_name", "dxjbi027n");
        config.put("api_key", "215856888986276");
        config.put("api_secret", "5WPkt96H4frKeJMsNlRR4hQ3UQU");
        config.put("secure", true);
        return new Cloudinary(config);
    }

}
