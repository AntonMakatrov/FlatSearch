package by.itacademy.user_service.service;

import by.itacademy.user_service.service.api.IUserPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordEncoder implements IUserPasswordEncoder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    @Override
    public Boolean passwordMatches(String plainPassword, String encodedPassword) {
        return passwordEncoder.matches(plainPassword, encodedPassword);
    }

}
