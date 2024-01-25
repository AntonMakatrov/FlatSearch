package by.itacademy.user_service.transformer;

import by.itacademy.user_service.core.dto.UserCreateDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.core.dto.UserDetailsDTO;
import by.itacademy.user_service.core.dto.UserRegistrationDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.entity.UserRole;
import by.itacademy.user_service.core.entity.UserStatus;
import by.itacademy.user_service.service.api.IUserPasswordEncoder;
import by.itacademy.user_service.transformer.api.IUserTransformer;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Optional;

@Component
public class UserTransformer implements IUserTransformer {

    private final IUserPasswordEncoder userPasswordEncoder;

    public UserTransformer(IUserPasswordEncoder userPasswordEncoder) {
        this.userPasswordEncoder = userPasswordEncoder;
    }

    @Override
    public UserDTO transformInfoDtoFromEntity(UserEntity user) {
        return new UserDTO()
                .setId(user.getId())
                .setMail(user.getMail())
                .setFio(user.getFio())
                .setRole(user.getRole())
                .setStatus(user.getStatus())
                .setDt_create(user.getCreationDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .setDt_update(user.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
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
    public UserCreateDTO transformCreationDtoFromRegistrationDto(UserRegistrationDTO userRegistrationDto) {
        return new UserCreateDTO().setMail(userRegistrationDto.getMail())
                .setPassword(userRegistrationDto.getPassword())
                .setFio(userRegistrationDto.getFio())
                .setStatus(UserStatus.WAITING_ACTIVATION)
                .setRole(UserRole.USER);
    }
}
