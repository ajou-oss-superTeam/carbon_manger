package com.oss.carbonadministrator.exception.exceptionhandler;

import com.oss.carbonadministrator.controller.response.ResponseDto;
import com.oss.carbonadministrator.exception.AlreadyExistEmailException;
import com.oss.carbonadministrator.exception.AlreadyExistNicknameException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 발생할 수 있는 예외 상황에 대해 ResponseDto 객체를 리턴해주기 위한 핸들러 클래스.
 */
@RestControllerAdvice(basePackages = "com.oss.carbonadministrator.controller")
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AlreadyExistEmailException.class)
    @ResponseBody
    public ResponseDto alreadyExistEmailException(AlreadyExistEmailException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ResponseDto.fail("이메일 중복 확인 실패", "AlreadyExistEmail", e.getMessage());
    }

    @ExceptionHandler(AlreadyExistNicknameException.class)
    @ResponseBody
    public ResponseDto alreadyExistNicknameException(AlreadyExistNicknameException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ResponseDto.fail("닉네임 중복 확인 실패", "AlreadyExistNickname", e.getMessage());
    }


}
