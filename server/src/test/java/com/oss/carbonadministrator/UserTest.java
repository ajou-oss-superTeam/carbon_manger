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
