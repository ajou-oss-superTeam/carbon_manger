package com.oss.carbonadministrator.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * 현재 모든 ip 응답 허용으로 설정
 */
@Configuration
public class CorsConfig {

    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용
        config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청을 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return new org.springframework.web.filter.CorsFilter(source);
    }
}
