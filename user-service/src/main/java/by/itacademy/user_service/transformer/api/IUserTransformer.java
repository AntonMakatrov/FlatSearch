package by.itacademy.user_service.transformer.api;


import by.itacademy.user_service.core.dto.UserCreateDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.core.dto.UserDetailsDTO;
import by.itacademy.user_service.core.dto.UserRegistrationDTO;
import by.itacademy.user_service.core.entity.UserEntity;

import java.util.Optional;

public interface IUserTransformer {
    UserDTO transformInfoDtoFromEntity(UserEntity user);

    UserEntity transformEntityFromCreateDto(UserCreateDTO userCreationDto);

    UserCreateDTO transformCreationDtoFromRegistrationDto(UserRegistrationDTO userRegistrationDto);
}
