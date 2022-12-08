package com.mikarte.javasecurityjwt.config;

import com.mikarte.javasecurityjwt.model.User;
import com.mikarte.javasecurityjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public JwtUserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByName(username);
        return JwtUserDetails.fromUserToJwtUserDetails(user);
    }
}