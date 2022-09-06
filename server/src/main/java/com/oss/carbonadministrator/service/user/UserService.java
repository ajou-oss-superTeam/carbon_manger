package com.oss.carbonadministrator.service.user;

import com.oss.carbonadministrator.domain.User;
import com.oss.carbonadministrator.domain.User.Role;
import com.oss.carbonadministrator.dto.response.user.SignupResponse;
import com.oss.carbonadministrator.exception.AlreadyExistEmailException;
import com.oss.carbonadministrator.exception.AlreadyExistNicknameException;
import com.oss.carbonadministrator.repository.UserRepository;
import com.oss.carbonadministrator.service.command.SignUpCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignupResponse signUp(SignUpCommand commandDto) {
        if (checkValidEmail(commandDto.getEmail())) {
            throw new AlreadyExistEmailException("해당 이메일은 이미 존재합니다.");
        }

        if (checkValidNickname(commandDto.getNickname())) {
            throw new AlreadyExistNicknameException("해당 닉네임은 이미 존재합니다.");
        }

        String encodedPw = passwordEncoder.encode(commandDto.getPassword());

        User user = User.builder()
            .email(commandDto.getEmail())
            .nickname(commandDto.getNickname())
            .password(encodedPw)
            .province(commandDto.getProvince())
            .city(commandDto.getCity())
            .role(Role.ROLE_USER)
            .build();

        userRepository.saveAndFlush(user);
        return new SignupResponse(commandDto.getEmail(), commandDto.getNickname());
    }

    public boolean checkValidEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkValidNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
