package by.itacademy.user_service.service.api;

import by.itacademy.user_service.core.dto.UserLoginDTO;

public interface IAuthorizationService {
    String login(UserLoginDTO user);
}
