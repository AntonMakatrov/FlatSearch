package by.itacademy.user_service.service.api;

import by.itacademy.user_service.core.dto.UserLoginDTO;
import by.itacademy.user_service.core.dto.UserRegistrationDTO;

public interface IAuthenticationService {
    void registerUser(UserRegistrationDTO userRegistrationDto);

    String login(UserLoginDTO userLoginDto);
}
