package com.mikarte.javasecurityjwt.repository;

import com.mikarte.javasecurityjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User repository interface {@link User}
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Get user by name
     * @param name {@link String}
     * @return {@link User}
     */
    User findUserByName(String name);
}
