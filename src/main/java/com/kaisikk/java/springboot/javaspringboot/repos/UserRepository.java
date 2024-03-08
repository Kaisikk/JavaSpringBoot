package com.kaisikk.java.springboot.javaspringboot.repos;

import com.kaisikk.java.springboot.javaspringboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
