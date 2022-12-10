package com.mikarte.javasecurityjwt.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Registration Request Class, has fields:
 * {@link RegistrationRequest#name},
 * {@link RegistrationRequest#password}
 */
@Data
public class RegistrationRequest {
    /**Name of user*/
    @NotEmpty
    private String name;
    /**Password*/
    @NotEmpty
    private String password;
}
