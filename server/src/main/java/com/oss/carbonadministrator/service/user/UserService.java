package com.oss.carbonadministrator.service.user;

import com.oss.carbonadministrator.domain.User;
import com.oss.carbonadministrator.exception.AlreadyExistEmailException;
import com.oss.carbonadministrator.exception.GeneralExceptions;
import com.oss.carbonadministrator.repository.UserRepository;
import com.oss.carbonadministrator.service.command.SignUpCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public User userLogin(String id, String pw) {
        Optional<User> user = userRepository.findByEmailAndPassword(id, pw);

        if (user.isEmpty()) {
            throw new GeneralExceptions("잘못된 Email 혹은 비밀번호입니다.");
        }

        return user.get();
    }

    public void signUp(SignUpCommand commandDto) {
        if (isExistEmail(commandDto)) {
            throw new AlreadyExistEmailException("해당 이메일은 이미 존재합니다.");
        }

        String encodedPw = passwordEncoder.encode(commandDto.getPassword());

        User user = User.builder()
            .email(commandDto.getEmail())
            .nickname(commandDto.getNickname())
            .password(encodedPw)
            .build();

        userRepository.saveAndFlush(user);
    }

    private boolean isExistEmail(SignUpCommand commandDto) {
        return userRepository.existsByEmail(commandDto.getEmail());
    }
}
