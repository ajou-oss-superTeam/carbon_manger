/**
 *  Copyright 2022 Carbon_Developers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.oss.carbonadministrator.exception.exceptionhandler;

import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.exception.image.ImgUploadFailException;
import com.oss.carbonadministrator.exception.user.AlreadyExistEmailException;
import com.oss.carbonadministrator.exception.user.AlreadyExistNicknameException;
import com.oss.carbonadministrator.exception.user.HasNoUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 발생할 수 있는 예외 상황에 대해 ResponseDto 객체를 리턴해주기 위한 핸들러 클래스.
 */
@RestControllerAdvice(basePackages = "com.oss.carbonadministrator.controller")
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AlreadyExistEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDto alreadyExistEmailException(AlreadyExistEmailException e) {
        return ResponseDto.fail("이메일 중복 확인 실패", "AlreadyExistEmail", e.getMessage());
    }

    @ExceptionHandler(AlreadyExistNicknameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDto alreadyExistNicknameException(AlreadyExistNicknameException e) {
        return ResponseDto.fail("닉네임 중복 확인 실패", "AlreadyExistNickname", e.getMessage());
    }

    @ExceptionHandler(ImgUploadFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDto imgUploadFailException(ImgUploadFailException e) {
        return ResponseDto.fail("이미지 업로드 실패", "ImgUploadFailException", e.getMessage());
    }

    @ExceptionHandler(HasNoUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDto hasNoUserException(HasNoUserException e) {
        return ResponseDto.fail("존재하지 않는 유저", "HasNoUserException", e.getMessage());
    }
}
