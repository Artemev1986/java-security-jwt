package com.mikarte.javasecurityjwt.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthenticationRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
}
