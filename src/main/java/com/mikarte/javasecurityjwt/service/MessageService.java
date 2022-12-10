package com.mikarte.javasecurityjwt.service;

import com.mikarte.javasecurityjwt.model.Message;
import com.mikarte.javasecurityjwt.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Message service, has field:
 * {@link MessageService#messageRepository},
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    /**Messages repository {@link MessageRepository}*/
    private final MessageRepository messageRepository;

    /**
     * Add new message
     * @param message {@link Message}
     */
    public void addMessage(Message message) {
        messageRepository.save(message);
    }

    /**
     * Get messages
     * @param name {@link String}
     * @param size {@link Integer}
     * @return {@link List<Message>}
     */
    public List<Message> getMessagesByName(String name, Integer size) {
        Pageable page = PageRequest.of(0, size, Sort.by("id").descending());
        return messageRepository.findByName(name, page);
    }
}
