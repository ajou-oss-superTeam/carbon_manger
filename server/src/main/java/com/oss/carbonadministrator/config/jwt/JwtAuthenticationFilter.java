package com.oss.carbonadministrator.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oss.carbonadministrator.controller.request.LoginRequest;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        log.info("jwtAuthenticationFilter: 로그인 시도중");

        // request에 있는 user의 email과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginRequest loginRequestDto = null;

        try {
            loginRequestDto = om.readValue(request.getInputStream(), loginRequestDto.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // 유저네임패스워드 토큰 생성


        return super.attemptAuthentication(request, response);
    }
}
