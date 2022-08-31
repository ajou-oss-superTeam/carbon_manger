package com.oss.carbonadministrator;

import com.oss.carbonadministrator.repository.UserRepository;
import com.oss.carbonadministrator.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    @Transactional(readOnly = true)
    public void loginTestFail() {
        try {
            userService.userLogin("dfldlk@gmail.com", "$2a$10$G5gG7jrYPJUA2YZYnahJk");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void loginTestPass() {
        try {
            userService.userLogin("dfldlk@gmail.com", "$2a$10$G5gG7jrYPJUA2YZYnahJk.O3yKWYJYKXgwhRAzom4w1bQl0BaX9Lu");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
