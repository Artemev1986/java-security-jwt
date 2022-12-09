package com.mikarte.javasecurityjwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikarte.javasecurityjwt.dto.AuthenticationRequest;
import com.mikarte.javasecurityjwt.dto.UserDto;
import com.mikarte.javasecurityjwt.jwt.JwtUtil;
import com.mikarte.javasecurityjwt.mapper.UserMapper;
import com.mikarte.javasecurityjwt.model.User;
import com.mikarte.javasecurityjwt.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest {

    private final ObjectMapper objectMapper;

    private final MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Test
    void registerUser() throws Exception {
        User user = new User();
        user.setName("Mikhail");
        user.setPassword("password");
        UserDto userDto = UserMapper.toUserDto(user);

        Mockito
                .when(userService.addUser(Mockito.any()))
                .thenReturn(userDto);

        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsString(user))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    void authentication() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setName("Mikhail");
        authenticationRequest.setPassword("password");
        User user = new User();
        user.setId(1L);
        user.setName(authenticationRequest.getName());
        user.setPassword(authenticationRequest.getPassword());

        Mockito
                .when(userService.findByLoginAndPassword(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(user);

        String token = Jwts.builder()
                .setSubject(user.getName())
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
        Mockito
                .when(jwtUtil.generateToken(Mockito.anyString()))
                .thenReturn(token);

        mockMvc.perform(post("/authentication")
                        .content(objectMapper.writeValueAsString(authenticationRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }
}