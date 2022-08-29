package com.oss.carbonadministrator.service.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCommand {

    private String email;
    private String nickname;
    private String password;
}
