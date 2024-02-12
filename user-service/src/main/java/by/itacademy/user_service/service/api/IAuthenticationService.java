package by.itacademy.user_service.service.api;

import by.itacademy.user_service.core.dto.UserLoginDTO;
import by.itacademy.user_service.core.dto.UserRegistrationDTO;
import by.itacademy.user_service.core.entity.UserEntity;

public interface IAuthenticationService {
    UserEntity registerUser(UserRegistrationDTO userRegistrationDto);

    String login(UserLoginDTO userLoginDto);
}
