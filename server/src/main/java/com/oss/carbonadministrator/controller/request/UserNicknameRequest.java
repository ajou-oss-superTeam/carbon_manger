package com.oss.carbonadministrator.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNicknameRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
