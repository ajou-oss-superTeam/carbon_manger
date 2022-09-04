package com.oss.carbonadministrator.dto.request.user;

import com.oss.carbonadministrator.service.command.SignUpCommand;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "주소를 입력해주세요")
    private String province; // 도

    @NotBlank(message = "주소를 입력해주세요")
    private String city; // 시,군,구

    /*
     * SignUpCommand 객체 생성을 위한 팩토리 메서드
     */
    public static SignUpCommand toCommand(SignUpRequest requestDto) {
        return new SignUpCommand(
            requestDto.getEmail(),
            requestDto.getNickname(),
            requestDto.getPassword(),
            requestDto.getProvince(),
            requestDto.getCity()
        );
    }
}
