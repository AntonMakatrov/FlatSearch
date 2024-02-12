package by.itacademy.flats_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import by.itacademy.flats_service.config.properties.JwtProperty;
import by.itacademy.flats_service.core.dto.UserDetailsDTO;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtHandler {

    private final JwtProperty jwtProperty;
    private final ObjectMapper objectMapper;

    public JwtHandler(JwtProperty jwtProperty, ObjectMapper objectMapper) {
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
