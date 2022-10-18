package com.oss.carbonadministrator.service.user;

import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.domain.user.User.Role;
import com.oss.carbonadministrator.dto.request.user.LoginRequest;
import com.oss.carbonadministrator.dto.request.user.UserEmailRequest;
import com.oss.carbonadministrator.dto.response.user.SignupResponse;
import com.oss.carbonadministrator.dto.response.user.UserInfoResponse;
import com.oss.carbonadministrator.exception.user.AlreadyExistEmailException;
import com.oss.carbonadministrator.exception.user.AlreadyExistNicknameException;
import com.oss.carbonadministrator.repository.user.UserRepository;
import com.oss.carbonadministrator.service.command.SignUpCommand;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
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
            .count(0)
            .build();

        userRepository.saveAndFlush(user);
        return new SignupResponse(commandDto.getEmail(), commandDto.getNickname());
    }

    @Transactional
    public boolean checkValidEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public boolean checkValidNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(UserEmailRequest requestDto){
        Optional targetUser = userRepository.findByEmail(requestDto.getEmail());

        if (targetUser.isEmpty()){
            return null;
        }

        UserInfoResponse result = new UserInfoResponse((User) targetUser.get());

        return result;
    }

    @Transactional(readOnly = false)
    public boolean changePW(LoginRequest requestDto){
        Optional target = userRepository.findByEmail(requestDto.getEmail());
        if (target.isEmpty()){
            return false;
        }

        User targetUser = (User)target.get();

        String encodedPw = passwordEncoder.encode(requestDto.getPassword());

        targetUser.setPassword(encodedPw);

        userRepository.saveAndFlush(targetUser);

        return true;
    }
}
