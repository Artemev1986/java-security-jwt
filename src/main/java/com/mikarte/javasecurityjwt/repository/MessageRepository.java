package com.mikarte.javasecurityjwt.repository;

import com.mikarte.javasecurityjwt.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Message repository interface {@link Message}
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Get messages by name
     * @param name {@link String}
     * @param page {@link Pageable}
     * @return List of {@link Message}
     */
    List<Message> findByName(String name, Pageable page);
}
