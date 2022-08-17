package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByPid(Long pid);

    public Optional<User> findById(String id);

    public Optional<User> findByIdAndPw(String id, String pw);
}
