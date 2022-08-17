package com.oss.carbonadministrator.service;

import com.oss.carbonadministrator.entity.User;
import com.oss.carbonadministrator.exception.EntityNotFoundException;
import com.oss.carbonadministrator.exception.GeneralExceptions;
import com.oss.carbonadministrator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(String id){
        return userRepository.findById(id).get();
    }

    public User userLogin(String id, String pw){
        Optional<User> user = userRepository.findByIdAndPw(id, pw);

        if(!user.isPresent()){
            throw new GeneralExceptions("잘못된 Email 혹은 비밀번호입니다.");
        }

        return user.get();
    }

    public User createUser(String id, String pw){
        if(id == null) {
            throw new EntityNotFoundException("user", "유저: "+ id + "가 null값입니다.");
        }

        Optional<User> findUser = userRepository.findById(id);
        if(findUser.isPresent()) {
            throw new GeneralExceptions("해당 이메일은 이미 계정이 존재합니다.");
        }

        if(pw == null) {
            throw new EntityNotFoundException("user", "유저: "+ pw + "가 null값입니다.");
        }

        User user = new User();

        user.setId(id);
        user.setPw(pw);
        userRepository.saveAndFlush(user);

        return user;
    }
}
