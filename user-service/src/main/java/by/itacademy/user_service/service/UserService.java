package by.itacademy.user_service.service;

import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.core.dto.UserDetailsDTO;
import by.itacademy.user_service.core.entity.UserRole;
import by.itacademy.user_service.core.entity.UserStatus;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.exceptions.InternalServerErrorException;
import by.itacademy.user_service.repository.UserRepository;
import by.itacademy.user_service.service.api.IUserPasswordEncoder;
import by.itacademy.user_service.service.api.IUserService;
import by.itacademy.user_service.service.api.IVerificationQueueService;
import by.itacademy.user_service.transformer.UserTransformer;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserTransformer userTransformer;
    private final ModelMapper modelMapper;
    private final IVerificationQueueService verificationQueueService;
    private final IUserPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserTransformer userTransformer, ModelMapper modelMapper, IVerificationQueueService verificationQueueService,
                       IUserPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
        this.modelMapper = modelMapper;
        this.verificationQueueService = verificationQueueService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO findInfoAbout() {
        UserDetailsDTO userDetails = (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findById(userDetails.getId());
        UserEntity asd = convertToEntity(userEntity);
        return userTransformer.transformInfoDtoFromEntity(asd);
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        List<UserDTO> dtoList = entityPage.stream()
                .map(userTransformer::transformInfoDtoFromEntity)
                .toList();
        return new PageImpl<UserDTO>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());

    }

    //TODO переделать без OPTIONAL
    @Override
    public Optional<UserEntity> findById(UUID id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(UserEntity user) {
        UserEntity entity = new UserEntity();
        entity.setMail(user.getMail());
        entity.setFio(user.getFio());
        entity.setRole(UserRole.USER);
        entity.setStatus(UserStatus.WAITING_ACTIVATION);
        entity.setPassword(passwordEncoder.encodePassword(user.getPassword()));

        try{
            this.userRepository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        this.verificationQueueService.add(entity);
    }

    @Override
    @Transactional
    public void update(UserEntity entity, UUID id, LocalDateTime dtUpdate) {
        Optional<UserEntity> optional = findById(id);
        UserEntity user = convertToEntity(optional);
        user.setMail(entity.getMail());
        user.setFio(entity.getFio());
        user.setRole(entity.getRole());
        user.setStatus(entity.getStatus());
        user.setPassword(passwordEncoder.encodePassword(entity.getPassword()));

        try {
            this.userRepository.save(user);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void activate(String mail){
        Optional<UserEntity> optional = userRepository.findByMail(mail);
        UserEntity user = convertToEntity(optional);
        user.setStatus(UserStatus.ACTIVATED);

        try {
            this.userRepository.save(user);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private UserEntity convertToEntity(Optional<UserEntity> entity) {
        return modelMapper.map(entity, UserEntity.class);
    }
}
