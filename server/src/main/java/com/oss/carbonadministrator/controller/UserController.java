package com.oss.carbonadministrator.controller;

import com.oss.carbonadministrator.dto.request.user.SignUpRequest;
import com.oss.carbonadministrator.dto.request.user.UserEmailRequest;
import com.oss.carbonadministrator.dto.request.user.UserNicknameRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.dto.response.user.SignupResponse;
import com.oss.carbonadministrator.exception.AlreadyExistEmailException;
import com.oss.carbonadministrator.exception.AlreadyExistNicknameException;
import com.oss.carbonadministrator.service.user.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
