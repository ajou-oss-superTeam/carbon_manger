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
package com.oss.carbonadministrator.controller.user;

import com.oss.carbonadministrator.dto.request.user.LoginRequest;
import com.oss.carbonadministrator.dto.request.user.SignUpRequest;
import com.oss.carbonadministrator.dto.request.user.UserEmailRequest;
import com.oss.carbonadministrator.dto.request.user.UserNicknameRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.dto.response.user.ChangePWResponse;
import com.oss.carbonadministrator.dto.response.user.SignupResponse;
import com.oss.carbonadministrator.dto.response.user.UserInfoResponse;
import com.oss.carbonadministrator.exception.user.AlreadyExistEmailException;
import com.oss.carbonadministrator.exception.user.AlreadyExistNicknameException;
import com.oss.carbonadministrator.service.user.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
     * 이메일 중복 여부 판단 API
     */
    @PostMapping("/user-email/exists")
    public ResponseDto checkEmailDuplicated(@RequestBody @Valid UserEmailRequest requestDto) {
        boolean isExistEmail = userService.checkValidEmail(requestDto.getEmail());
        if (isExistEmail) {
            throw new AlreadyExistEmailException("해당 이메일은 이미 존재합니다.");
        }
        return ResponseDto.success(null, "사용 가능한 이메일입니다.");
    }

    /*
     * 닉네임 중복 여부 판단 API
     */
    @PostMapping("/user-nickname/exists")
    public ResponseDto checkNicknameDuplicated(@RequestBody @Valid UserNicknameRequest requestDto) {
        boolean isExistNickname = userService.checkValidNickname(requestDto.getNickname());
        if (isExistNickname) {
            throw new AlreadyExistNicknameException("해당 닉네임은 이미 존재합니다.");
        }
        return ResponseDto.success(null, "사용 가능한 닉네임입니다.");
    }

    /*
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseDto signUp(@Valid @RequestBody SignUpRequest requestDto) {
        SignupResponse responseDto = userService.signUp(SignUpRequest.toCommand(requestDto));
        return ResponseDto.success(responseDto, "회원 가입 성공");
    }

    /*
     * 시큐리티 로그인 API => 시큐리티 Config 내 구현 완료
     * /api/user/login"
     */

    @PostMapping("/mypage/info")
    public ResponseDto userInfo(@Valid @RequestBody UserEmailRequest requestDto) {
        UserInfoResponse responseDto = userService.getUserInfo(requestDto);
        return ResponseDto.success(responseDto, "user info");
    }

    @PutMapping("/mypage/passwd")
    public ResponseDto changePW(@Valid @RequestBody LoginRequest requestDto){
        ChangePWResponse responseDto = new ChangePWResponse();
        boolean result = userService.changePW(requestDto);
        responseDto.setResult(result);
        if (result){
            return ResponseDto.success(responseDto, "user info");
            }
        else{
            return ResponseDto.fail("비밀번호 변경 실패", "-1", "비밀번호 변경 실패");
        }
    }

}
