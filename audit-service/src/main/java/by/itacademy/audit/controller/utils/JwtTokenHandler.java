package by.itacademy.audit.controller.utils;

import by.itacademy.audit.config.properites.JWTProperty;
import by.itacademy.audit.core.dto.UserDetailsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtTokenHandler {

    private final JWTProperty jwtProperty;
    private final ObjectMapper objectMapper;

    public JwtTokenHandler(JWTProperty jwtProperty, ObjectMapper objectMapper) {
        this.jwtProperty = jwtProperty;
        this.objectMapper = objectMapper;
    }

    public UserDetailsDTO getUserDetailsDtoFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return convertDtoFromJson(claims.getSubject());
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException exception) {
            log.error("Invalid JWT signature " + exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Invalid JWT token " + exception.getMessage());
        } catch (ExpiredJwtException exception) {
            log.error("Expired JWT token " + exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Unsupported JWT token " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("JWT claims string is empty " + exception.getMessage());
        }
        return false;
    }

    private UserDetailsDTO convertDtoFromJson(String json) {
        try {
            return objectMapper.readValue(json, UserDetailsDTO.class);
        } catch (Exception exception) {
            throw new RuntimeException("Object mapper filed while reading value: " + exception.getMessage());
        }
    }
}
