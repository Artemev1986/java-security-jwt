package com.mikarte.javasecurityjwt.controller;

import com.mikarte.javasecurityjwt.jwt.JwtUtil;
import com.mikarte.javasecurityjwt.dto.AuthenticationRequest;
import com.mikarte.javasecurityjwt.dto.AuthenticationResponse;
import com.mikarte.javasecurityjwt.dto.RegistrationRequest;
import com.mikarte.javasecurityjwt.dto.UserDto;
import com.mikarte.javasecurityjwt.model.User;
import com.mikarte.javasecurityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setName(registrationRequest.getName());
        UserDto userDto = userService.addUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/authentication")
    public ResponseEntity<Object> authentication(@RequestBody @Valid AuthenticationRequest request) {
        User user = userService.findByLoginAndPassword(request.getName(), request.getPassword());
        String token = jwtUtil.generateToken(user.getName());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}