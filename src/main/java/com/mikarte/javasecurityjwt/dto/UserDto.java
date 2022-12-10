package com.mikarte.javasecurityjwt.dto;

import lombok.Data;

/**
 * User DTO Class, has fields:
 * {@link UserDto#id},
 * {@link UserDto#name}
 */
@Data
public class UserDto {
    /**User identifier*/
    private Long id;
    /**User name*/
    private String name;
}
