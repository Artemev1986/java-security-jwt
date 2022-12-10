package com.mikarte.javasecurityjwt.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Authentication Request Class, has fields:
 * {@link AuthenticationRequest#name},
 * {@link AuthenticationRequest#password}
 */
@Data
public class AuthenticationRequest {
    /**Name of user*/
    @NotEmpty
    private String name;
    /**Password*/
    @NotEmpty
    private String password;
}
