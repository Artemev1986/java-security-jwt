package com.mikarte.javasecurityjwt.controller;

import com.mikarte.javasecurityjwt.config.JwtUtil;
import com.mikarte.javasecurityjwt.exception.ForbiddenException;
import com.mikarte.javasecurityjwt.model.Message;
import com.mikarte.javasecurityjwt.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final JwtUtil jwtProvider;
    private static final String AUTHORIZATION = "Authorization";

    @PostMapping("/messages")
    public ResponseEntity<Object> addMessage(@RequestHeader(AUTHORIZATION) String token,
                                             @RequestBody @Valid Message newMessage) {
        String name = jwtProvider.getLoginFromToken(token.substring(7));
        if (!name.equals(newMessage.getName())) {
            throw new ForbiddenException("The name from the requestBody does not match the name from the token");
        }

        String history = "history ";
        if (newMessage.getMessage().startsWith(history)) {
            String sizeString = newMessage.getMessage().substring(history.length());
            int size = 0;
            boolean hasInt;
            try {
                size = Integer.parseInt(sizeString);
                hasInt = true;
            } catch (NumberFormatException e) {
                log.info("not a number");
                hasInt = false;
            }
            if (hasInt) {
                List<Message> messages = messageService.getMessagesByName(name, size);
                return new ResponseEntity<>(messages, HttpStatus.OK);
            }
        }

        Message message = new Message();
        message.setName(newMessage.getName());
        message.setMessage(newMessage.getMessage());
        messageService.addMessage(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
