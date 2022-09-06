package com.oss.carbonadministrator.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oss.carbonadministrator.config.security.CustomUserDetails;
import com.oss.carbonadministrator.dto.request.user.LoginRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.dto.response.user.LoginResponse;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    /*
     * 로그인 성공시 Response 전달
     */
    private static void sendSuccessLoginResponse(HttpServletResponse response,
        CustomUserDetails customUserDetails)
        throws IOException {
        String email = customUserDetails.getUser().getEmail();
        String nickname = customUserDetails.getUser().getNickname();
        LoginResponse responseDto = new LoginResponse(email, nickname);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper ob = new ObjectMapper();
        String resultDto = ob.writeValueAsString(ResponseDto.success(responseDto, "로그인 성공"));
        response.getWriter().write(resultDto);
    }

    /*
     * attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수 실행
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        log.info("jwtAuthenticationFilter: 로그인 시도중");

        // request에 있는 user의 email과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginRequest loginRequestDto;

        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginRequestDto.getEmail(), loginRequestDto.getPassword());
        log.info("JwtAuthenticationFilter : 인증 확인용 토큰 생성");

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 로그인 확인
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("로그인 완료 됨. Authentication: {}", customUserDetails.getUsername());

        return authentication;
    }

    /*
     * 로그인 완료 후 JWT 토큰 생성 후 토큰을 ResponseHeader에 전달, Login Success ResponseBody 전달
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
            .withSubject(customUserDetails.getUsername())
            .withExpiresAt(
                new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)
            ) // 토큰 만료 시간
            .withClaim("id", customUserDetails.getUser().getPid()) // 변경 가능
            .withClaim("username", customUserDetails.getUser().getEmail()) // 변경 가능
            .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        sendSuccessLoginResponse(response, customUserDetails);
    }
}
