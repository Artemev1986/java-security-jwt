package com.mikarte.javasecurityjwt.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class for working with a token, has field:
 * {@link JwtUtil#jwtSecret}
 */
@Component
@Slf4j
public class JwtUtil {

    /**Link to decryption key {@link String}*/
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Generation token
     * @param name {@link String}
     * @return token {@link String}
     */
    public String generateToken(String name) {
        return Jwts.builder()
                .setSubject(name)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

    }

    /**
     * Validation token
     * @param token {@link String}
     * @return true if valid, else false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
    }

    /**
     * Get name from token
     * @param token {@link String}
     * @return name {@link String}
     */
    public String getNameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}