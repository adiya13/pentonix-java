package com.pentonix.apache.apiservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	@Bean
    @ConfigurationProperties("istint.cors")
    public CorsData corsData() {
        return new CorsData();
    }

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsData cData = corsData();

        registry
            .addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowedOrigins(cData.getAllowedOrigins().toArray(new String[] {}))
            .allowCredentials(cData.isAllowCredentials());
    }
}
