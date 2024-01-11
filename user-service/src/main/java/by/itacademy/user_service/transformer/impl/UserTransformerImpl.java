package by.itacademy.user_service.transformer.impl;

import by.itacademy.user_service.core.dto.UserCreateDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.core.dto.UserRegistrationDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.entity.UserRole;
import by.itacademy.user_service.core.entity.UserStatus;
import by.itacademy.user_service.service.api.IUserPasswordEncoder;
import by.itacademy.user_service.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTransformerImpl implements UserTransformer {

    @Autowired
    private IUserPasswordEncoder userPasswordEncoder;

    @Override
    public UserDTO transformInfoDtoFromEntity(UserEntity user) {
        return new UserDTO()
                .setId(user.getId())
                .setMail(user.getMail())
                .setFio(user.getFio())
                .setRole(user.getRole())
                .setStatus(user.getStatus())
                .setDt_create(user.getCreationDate())
                .setDt_update(user.getUpdateDate());
    }

    @Override
    public UserCreateDTO transformCreateDtoFromEntity(UserEntity user) {
        return new UserCreateDTO()
                .setMail(user.getMail())
                .setPassword(user.getPassword())
                .setFio(user.getFio())
                .setRole(user.getRole())
                .setStatus(user.getStatus());
    }

    @Override
    public UserEntity transformEntityFromCreateDto(UserCreateDTO userCreationDto) {
        return new UserEntity()
                .setMail(userCreationDto.getMail())
                .setPassword(userPasswordEncoder.encodePassword(userCreationDto.getPassword()))
                .setFio(userCreationDto.getFio())
                .setRole(userCreationDto.getRole())
                .setStatus(userCreationDto.getStatus());
    }

    @Override
    public UserEntity transformEntityFromRegistrationDto(UserRegistrationDTO userRegistrationDto) {
        return new UserEntity()
                .setMail(userRegistrationDto.getMail())
                .setPassword(userPasswordEncoder.encodePassword(userRegistrationDto.getPassword()))
                .setFio(userRegistrationDto.getFio())
                .setRole(UserRole.USER)
                .setStatus(UserStatus.WAITING_ACTIVATION);
    }

}
