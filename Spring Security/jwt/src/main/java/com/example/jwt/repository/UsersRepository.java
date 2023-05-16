package com.example.jwt.repository;

import com.example.jwt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    public Users findByUsername(String username);

}
