package com.oss.carbonadministrator.controller;

import com.oss.carbonadministrator.controller.request.LoginRequest;
import com.oss.carbonadministrator.controller.request.SignUpRequest;
import com.oss.carbonadministrator.controller.response.ResponseDto;
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

    @PostMapping("/user-email/exists")
    public String checkEmailDuplicated() {
        return "ok";
    }

    @PostMapping("/user-nickname/exists")
    public String checkNicknameDuplicated() {
        return "ok";
    }


    @PostMapping("/signup")
    public ResponseDto signUp(@Valid @RequestBody SignUpRequest requestDto) {
        userService.signUp(SignUpRequest.toCommand(requestDto));
        return ResponseDto.success(null, "회원 가입 성공");
    }

    @PostMapping("/login")
    public ResponseDto login(@Valid @RequestBody LoginRequest requestDto) {

        return ResponseDto.success(null, "로그인 성공");
    }
}
