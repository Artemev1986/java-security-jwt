package com.mikarte.javasecurityjwt.repository;

import com.mikarte.javasecurityjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByName(String name);
}
