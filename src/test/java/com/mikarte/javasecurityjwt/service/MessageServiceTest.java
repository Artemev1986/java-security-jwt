package com.mikarte.javasecurityjwt.service;

import com.mikarte.javasecurityjwt.model.Message;
import com.mikarte.javasecurityjwt.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MessageServiceTest {

    private final MessageService messageService;

    @MockBean
    private final MessageRepository messageRepository;

    private final Message message = new Message();

    @BeforeEach
    void beforeEach() {
        message.setId(1L);
        message.setName("Mikhail");
        message.setMessage("message");
    }

    @Test
    void addMessage() {
        Message savedMessage = new Message();
        savedMessage.setId(1L);
        savedMessage.setName("Mikhail");
        savedMessage.setMessage("message");

        Mockito
                .when(messageRepository.save(any()))
                .thenReturn(savedMessage);

        messageService.addMessage(message);

        assertThat(message).isEqualTo(savedMessage);
    }

    @Test
    void getMessagesByName() {
        Mockito
                .when(messageRepository.findByName(anyString(), any()))
                .thenReturn(List.of(message));

        List<Message> messages = messageService.getMessagesByName(message.getName(), 1);

        assertThat(messages).isEqualTo(List.of(message));
    }
}