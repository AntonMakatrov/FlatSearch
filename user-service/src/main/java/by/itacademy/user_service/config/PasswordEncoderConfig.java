package by.itacademy.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder getEncoder() {
        int strength = 10;
        return new BCryptPasswordEncoder(strength, new SecureRandom());
    }
}
