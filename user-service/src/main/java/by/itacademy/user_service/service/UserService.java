package by.itacademy.user_service.service;

import by.itacademy.user_service.aop.Audited;
import by.itacademy.user_service.core.dto.UserCreateDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.core.dto.UserDetailsDTO;
import by.itacademy.user_service.core.dto.UserQueryDTO;
import by.itacademy.user_service.core.entity.UserStatus;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.exceptions.EntityNotFoundException;
import by.itacademy.user_service.core.exceptions.ValidationException;
import by.itacademy.user_service.repository.UserRepository;
import by.itacademy.user_service.service.api.IUserPasswordEncoder;
import by.itacademy.user_service.service.api.IUserService;
import by.itacademy.user_service.service.api.IVerificationQueueService;
import by.itacademy.user_service.transformer.api.IUserTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static by.itacademy.user_service.core.entity.AuditedAction.*;
import static by.itacademy.user_service.core.entity.EssenceType.USER;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final IUserTransformer userTransformer;
    private final IVerificationQueueService verificationQueueService;
    private final IUserPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, IUserTransformer userTransformer, IVerificationQueueService verificationQueueService,
                       IUserPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
        this.verificationQueueService = verificationQueueService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Audited(auditedAction = INFO_ABOUT_ME, essenceType = USER)
    public UserDTO findInfoAbout() {
        UserDetailsDTO userDetails = (UserDetailsDTO) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return findById(userDetails.getId());
    }

    @Override
    @Audited(auditedAction = INFO_ABOUT_ALL_USERS, essenceType = USER)
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        List<UserDTO> dtoList = entityPage.stream()
                .map(userTransformer::transformInfoDtoFromEntity)
                .toList();
        return new PageImpl<UserDTO>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());

    }

    @Override
    @Audited(auditedAction = INFO_ABOUT_USER_BY_ID, essenceType = USER)
    public UserDTO findById(UUID id) {
        UserEntity entity = this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        return this.userTransformer.transformInfoDtoFromEntity(entity);
    }

    @Override
    @Transactional
    public UserEntity save(UserCreateDTO user) {
        validateEmail(user.getMail());
        UserEntity userForSave = userRepository.saveAndFlush(userTransformer.transformEntityFromCreateDto(user));

        this.verificationQueueService.add(userForSave);
        return userForSave;
    }

    @Transactional
    @Override
    @Audited(auditedAction = CREATE_USER, essenceType = USER)
    public UserEntity createUser(UserCreateDTO userCreationDto) {
        validateEmail(userCreationDto.getMail());
        return userRepository.saveAndFlush(userTransformer.transformEntityFromCreateDto(userCreationDto));
    }

    @Override
    public UserQueryDTO getUserQueryDto(String email){
        return userRepository.findPasswordAndStatusByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User", email)
                );
    }

    @Override
    public UserDetailsDTO getUserDetailsDto(String email) {
        return userRepository.findIdFioAndRoleByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User", email));
    }
    @Override
    @Transactional
    @Audited(auditedAction = UPDATE_USER, essenceType = USER)
    public UserEntity update(UserCreateDTO dto, UUID id, LocalDateTime dtUpdate) {
        UserEntity userEntity = getUserById(id);
        userEntity
                .setMail(dto.getMail())
                .setPassword(passwordEncoder.encodePassword(dto.getPassword()))
                .setFio(dto.getFio())
                .setRole(dto.getRole())
                .setStatus(dto.getStatus());
        if (userEntity.getUpdateDate().truncatedTo(ChronoUnit.MILLIS).isEqual(dtUpdate)) {
            userRepository.saveAndFlush(userEntity);
        } else {
            throw new ValidationException("Неверный dt_update - " + dtUpdate
                    .toInstant(ZoneOffset.ofTotalSeconds(0))
                    .toEpochMilli());
        }
        return userEntity;
    }

    @Override
    @Transactional
    public void activate(String mail){
        userRepository.updateStatusByMail(UserStatus.ACTIVATED, mail);
    }

    private void validateEmail(String email){
        if (userRepository.existsByMail(email)) {
            throw new ValidationException("Данный почтовый адрес уже зарегистрирован. Проверьте правильность введенных данных");
        }
    }
    private UserEntity getUserById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }
}
