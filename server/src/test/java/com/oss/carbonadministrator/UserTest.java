package com.oss.carbonadministrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.dto.request.user.LoginRequest;
import com.oss.carbonadministrator.dto.request.user.UserEmailRequest;
import com.oss.carbonadministrator.dto.response.user.UserInfoResponse;
import com.oss.carbonadministrator.repository.user.UserRepository;
import com.oss.carbonadministrator.service.user.UserService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void getUserInfoTest(){
        UserEmailRequest testDto = new UserEmailRequest();
        testDto.setEmail("test22@gmail.com");

        UserInfoResponse result = userService.getUserInfo(testDto);

        assertEquals(result.getEmail(), "test22@gmail.com");
        assertEquals(result.getNickName(), "user2");
    }

    @Test
    @Ignore
    public void changePWTest(){
        LoginRequest requestDto = new LoginRequest("test22@gmail.com", "test1234!");

        assertTrue(userService.changePW(requestDto));

    }
}
