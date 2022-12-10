package com.mikarte.javasecurityjwt.service;

import com.mikarte.javasecurityjwt.dto.UserDto;
import com.mikarte.javasecurityjwt.mapper.UserMapper;
import com.mikarte.javasecurityjwt.model.User;
import com.mikarte.javasecurityjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * User service, has field:
 * {@link UserService#userRepository},
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**Users repository {@link UserRepository}*/
    private final UserRepository userRepository;

    /**
     * Add new user
     * @param user {@link User}
     * @return {@link UserDto}
     */
    public UserDto addUser(User user) {
        user.setPassword(user.getPassword());
        return UserMapper.toUserDto(userRepository.save(user));
    }

    /**
     * Search user by name
     * @param name {@link String}
     * @return {@link User}
     */
    public User findByName(String name) {
        User user = userRepository.findUserByName(name);
        if (user == null) {
            throw new EntityNotFoundException("User named " + name + " not found");
        }
        return user;
    }

    /**
     * Search user by name and password
     * @param name {@link String}
     * @param password {@link String}
     * @return {@link User}
     */
    public User findByLoginAndPassword(String name, String password) {
        User user = findByName(name);
        if (password.equals(user.getPassword())) {
            return user;
        } else {
            throw new EntityNotFoundException("There is no " + name + " in the database with this password");
        }
    }
}
