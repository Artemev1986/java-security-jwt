package com.mikarte.javasecurityjwt.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Message, contains fields:
 * {@link Message#id},
 * {@link Message#name},
 * {@link Message#message}
 */
@Data
@Entity
@Table(name = "messages")
public class Message {
    /**Message identifier*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**User name*/
    @NotEmpty
    private String name;
    /**Message*/
    @NotEmpty
    private String message;
}
