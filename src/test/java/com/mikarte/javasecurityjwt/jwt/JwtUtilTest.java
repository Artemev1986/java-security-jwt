package com.mikarte.javasecurityjwt.jwt;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class JwtUtilTest {

    private final JwtUtil jwtUtil;

    @Test
    void testToken() {
        String name = "Mikhail";

        String token = jwtUtil.generateToken(name);
        assertThat(jwtUtil.validateToken(token)).isEqualTo(true);
        assertThat(jwtUtil.getNameFromToken(token)).isEqualTo(name);
        assertThat(jwtUtil.validateToken(token + "noise")).isEqualTo(false);
    }
}