package com.mikarte.javasecurityjwt.dto;

import lombok.Data;

/**
 * Class containing the token, has field:
 * {@link AuthenticationResponse#token},
 */
@Data
public class AuthenticationResponse {
    /**token*/
    private String token;
}