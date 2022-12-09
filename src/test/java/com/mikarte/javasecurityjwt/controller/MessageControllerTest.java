package com.mikarte.javasecurityjwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikarte.javasecurityjwt.jwt.JwtUtil;
import com.mikarte.javasecurityjwt.model.Message;
import com.mikarte.javasecurityjwt.service.MessageService;
import com.mikarte.javasecurityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@WebMvcTest(controllers = MessageController.class)
class MessageControllerTest {

    private final ObjectMapper objectMapper;

    private final MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    MessageService messageService;

    @MockBean
    JwtUtil jwtUtil;

    private static final String AUTHORIZATION = "Authorization";
    private final Message message = new Message();
    @BeforeEach
    void beforeEach() {
        message.setName("Mikhail");
        message.setMessage("text");
    }

    @Test
    void addMessageCreated() throws Exception {

        Mockito
                .when(jwtUtil.validateToken(anyString()))
                .thenReturn(true);
        Mockito
                .when(jwtUtil.getNameFromToken(anyString()))
                .thenReturn(message.getName());

        mockMvc.perform(post("/messages")
                        .header(AUTHORIZATION, "test token")
                        .content(objectMapper.writeValueAsString(message))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(message)));
    }

    @Test
    void addMessageNotValidToken() throws Exception {

        Mockito
                .when(jwtUtil.validateToken(anyString()))
                .thenReturn(false);
        Mockito
                .when(jwtUtil.getNameFromToken(anyString()))
                .thenReturn(message.getName());

        mockMvc.perform(post("/messages")
                        .header(AUTHORIZATION, "test token")
                        .content(objectMapper.writeValueAsString(message))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void addMessageNotValidName() throws Exception {

        Mockito
                .when(jwtUtil.validateToken(anyString()))
                .thenReturn(true);
        Mockito
                .when(jwtUtil.getNameFromToken(anyString()))
                .thenReturn("unregisteredName");

        mockMvc.perform(post("/messages")
                        .header(AUTHORIZATION, "test token")
                        .content(objectMapper.writeValueAsString(message))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void getHistoryMessages() throws Exception {

        message.setMessage("history 1");

        Mockito
                .when(jwtUtil.validateToken(anyString()))
                .thenReturn(true);
        Mockito
                .when(jwtUtil.getNameFromToken(anyString()))
                .thenReturn(message.getName());

        Mockito
                .when(messageService.getMessagesByName(anyString(), anyInt()))
                .thenReturn(List.of(message));

        mockMvc.perform(post("/messages")
                        .header(AUTHORIZATION, "test token")
                        .content(objectMapper.writeValueAsString(message))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(message))));
    }
}