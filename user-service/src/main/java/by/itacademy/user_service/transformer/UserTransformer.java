package by.itacademy.user_service.transformer;


import by.itacademy.user_service.core.dto.UserCreateDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.core.dto.UserRegistrationDTO;
import by.itacademy.user_service.core.entity.UserEntity;

public interface UserTransformer {

    UserDTO transformInfoDtoFromEntity(UserEntity user);

    UserCreateDTO transformCreateDtoFromEntity(UserEntity user);

    UserEntity transformEntityFromCreateDto(UserCreateDTO userCreationDto);

    UserEntity transformEntityFromRegistrationDto(UserRegistrationDTO userRegistrationDto);
}
