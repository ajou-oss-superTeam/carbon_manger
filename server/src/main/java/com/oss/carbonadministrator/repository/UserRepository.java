package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPid(Long pid);

    Optional<User> findById(String email);

    Optional<User> findByIdAndPw(String email, String password);
}
