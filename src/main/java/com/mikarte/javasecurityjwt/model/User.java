package com.mikarte.javasecurityjwt.model;

import lombok.Data;

import javax.persistence.*;

/**
 * User, contains fields:
 * {@link User#id},
 * {@link User#name},
 * {@link User#password}
 */
@Data
@Entity
@Table(name = "users")
public class User {
    /**User identifier*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**User name*/
    private String name;
    /**User password*/
    private String password;
}
