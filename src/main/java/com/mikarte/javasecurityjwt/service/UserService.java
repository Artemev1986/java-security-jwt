package com.mikarte.javasecurityjwt.service;

import com.mikarte.javasecurityjwt.model.User;
import com.mikarte.javasecurityjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByName(String name) {
        User user = userRepository.findUserByName(name);
        if (user == null) {
            throw new EntityNotFoundException("User named " + name + " not found");
        }
        return user;
    }

    public User findByLoginAndPassword(String name, String password) {
        User user = findByName(name);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            } else {
                throw new EntityNotFoundException("There is no " + name + " in the database with this password");
            }
        }
        return null;
    }
}
