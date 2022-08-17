package com.oss.carbonadministrator;

import com.oss.carbonadministrator.entity.User;
import com.oss.carbonadministrator.repository.UserRepository;
import com.oss.carbonadministrator.service.UserService;
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
    public void loginTestFail(){
        try{
            userService.userLogin("test", "123");
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void loginTestPass(){
        try{
            userService.userLogin("test", "test");
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

//    @Test
    @Transactional(readOnly = false)
    public void createTestFail(){
        try{
            userService.createUser("test", "test");
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

//    @Test
    @Transactional(readOnly = false)
    public void createTestPass(){
        try{
            userService.createUser("test1", "test");

            userService.userLogin("test1", "test");
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }
}
