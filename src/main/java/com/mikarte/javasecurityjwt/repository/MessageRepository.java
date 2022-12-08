package com.mikarte.javasecurityjwt.repository;

import com.mikarte.javasecurityjwt.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByName(String name, Pageable page);
}
