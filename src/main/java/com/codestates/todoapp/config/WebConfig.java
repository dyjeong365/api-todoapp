package com.codestates.todoapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/v1/api/todos/**")
                .allowedOrigins("https://todobackend.com")
                .allowedMethods("GET","POST","PATCH","DELETE");
    }
}
