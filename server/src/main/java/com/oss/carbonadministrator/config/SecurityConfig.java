package com.oss.carbonadministrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .formLogin().disable()
            .httpBasic().disable()

            // TODO 추후 인가 미처리 url 추가
            .authorizeRequests()
            .antMatchers("/api/user/signup", "/api/user/login").permitAll()

            // 그 외 API는 인가 절차 수행
            .anyRequest().authenticated();

        return http.build();
    }
}
