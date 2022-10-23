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
