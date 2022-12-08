package com.mikarte.javasecurityjwt.service;

import com.mikarte.javasecurityjwt.model.Message;
import com.mikarte.javasecurityjwt.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void addMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getMessagesByName(String name, Integer size) {
        Pageable page = PageRequest.of(0, size, Sort.by("id").descending());
        return messageRepository.findByName(name, page);
    }
}
