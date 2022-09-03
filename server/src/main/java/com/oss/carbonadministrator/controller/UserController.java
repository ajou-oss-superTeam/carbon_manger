package com.oss.carbonadministrator.controller;

import com.oss.carbonadministrator.controller.request.SignUpRequest;
import com.oss.carbonadministrator.controller.request.UserEmailRequest;
import com.oss.carbonadministrator.controller.request.UserNicknameRequest;
import com.oss.carbonadministrator.controller.response.ResponseDto;
import com.oss.carbonadministrator.exception.AlreadyExistEmailException;
import com.oss.carbonadministrator.exception.AlreadyExistNicknameException;
import com.oss.carbonadministrator.service.user.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String home() {
        return "<h1>home</h1>";
    }

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
        userService.signUp(SignUpRequest.toCommand(requestDto));
        return ResponseDto.success(null, "회원 가입 성공");
    }

    /*
     * 시큐리티 로그인 API => 구현 완료
     * login API url : /api/user/login"
     */
}
