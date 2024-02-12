package by.itacademy.user_service.service;

import by.itacademy.user_service.aop.Audited;
import by.itacademy.user_service.core.exceptions.ValidationException;
import by.itacademy.user_service.utils.JwtTokenHandler;
import by.itacademy.user_service.core.dto.UserLoginDTO;
import by.itacademy.user_service.core.dto.UserQueryDTO;
import by.itacademy.user_service.core.dto.UserRegistrationDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.entity.UserStatus;
import by.itacademy.user_service.service.api.IAuthenticationService;
import by.itacademy.user_service.service.api.IUserPasswordEncoder;
import by.itacademy.user_service.service.api.IUserService;
import by.itacademy.user_service.transformer.api.IUserTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static by.itacademy.user_service.core.entity.AuditedAction.LOGIN;
import static by.itacademy.user_service.core.entity.AuditedAction.REGISTRATION;
import static by.itacademy.user_service.core.entity.EssenceType.USER;


@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;
    private final IUserTransformer userTransformer;
    private final JwtTokenHandler jwtTokenHandler;
    private final IUserPasswordEncoder passwordEncoder;

    public AuthenticationService(JwtTokenHandler jwtTokenHandler, IUserService userService, IUserTransformer userTransformer, IUserPasswordEncoder passwordEncoder) {
        this.jwtTokenHandler = jwtTokenHandler;
        this.userService = userService;
        this.userTransformer = userTransformer;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    @Audited(auditedAction = REGISTRATION, essenceType = USER)
    public UserEntity registerUser(UserRegistrationDTO userRegistrationDto) {
        return userService.save(userTransformer.transformCreationDtoFromRegistrationDto(userRegistrationDto));
    }

    @Override
    @Transactional
    @Audited(auditedAction = LOGIN, essenceType = USER)
    public String login(UserLoginDTO userLoginDto) {
        UserQueryDTO userQueryDto = userService.getUserQueryDto(userLoginDto.getMail());

        if (!passwordEncoder.passwordMatches(userLoginDto.getPassword(), userQueryDto.getPassword())) {
            throw new ValidationException("Неправильный пароль");
        }
        if (!userQueryDto.getStatus().equals(UserStatus.ACTIVATED)) {
            throw new ValidationException("Верификация провалилась или аккаунт деактивирован");
        }

        return jwtTokenHandler.generateAccessToken(userService.getUserDetailsDto(userLoginDto.getMail()));
    }
}
