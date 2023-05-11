package com.example.security1.repository;

import com.example.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


// CRUD 함수를 JpaRepository가 들고 있다
// @Repository라는 어노테이션이 없어도 IoC된다
public interface UserRepository extends JpaRepository<User, Long> {
    // findBy는 규칙 -> Username는 문법
    // select * from user where username = 1?
    public User findByUsername(String username);
}
