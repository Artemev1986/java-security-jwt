package com.mikarte.javasecurityjwt.controller;

import com.mikarte.javasecurityjwt.exception.ForbiddenException;
import com.mikarte.javasecurityjwt.jwt.JwtUtil;
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

/**
 * API for working with messages {@link Message}, has fields:
 * {@link MessageController#messageService},
 * {@link MessageController#jwtUtil},
 * {@link MessageController#AUTHORIZATION}
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    /**Messages Service*/
    private final MessageService messageService;
    /**Token Service*/
    private final JwtUtil jwtUtil;
    /**Header for authentication by bearer token*/
    private static final String AUTHORIZATION = "Authorization";

    /**
     *Adding a new message or uploading historical messages
     * @param bearerToken {@link String}
     * @param newMessage {@link Message}
     * @return {@link ResponseEntity}
     */
    @PostMapping("/messages")
    public ResponseEntity<Object> addMessage(@RequestHeader(AUTHORIZATION) String bearerToken,
                                             @RequestBody @Valid Message newMessage) {
        //Extracting the token from the header
        String token = bearerToken.substring(7);
        //validation token
        if (!jwtUtil.validateToken(token)) {
            throw new ForbiddenException("Token not valid");
        }

        //Extracting the name from the token
        String name = jwtUtil.getNameFromToken(token);
        //Comparing the name from the token and from the body
        if (!name.equals(newMessage.getName())) {
            throw new ForbiddenException("The name from the requestBody does not match the name from the token");
        }

        //Checking for a request to unload messages from the database
        String history = "history ";
        if (newMessage.getMessage().toLowerCase().startsWith(history)) {
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
