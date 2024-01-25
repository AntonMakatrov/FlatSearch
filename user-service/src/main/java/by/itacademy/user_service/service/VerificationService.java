package by.itacademy.user_service.service;

import by.itacademy.user_service.core.dto.VerificationDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.entity.VerificationEntity;
import by.itacademy.user_service.core.exceptions.ValidationException;
import by.itacademy.user_service.repository.UserRepository;
import by.itacademy.user_service.repository.VerificationRepository;
import by.itacademy.user_service.service.api.IVerificationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationService implements IVerificationService {
    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;

    public VerificationService(VerificationRepository verificationRepository, UserRepository userRepository) {
        this.verificationRepository = verificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void verify(VerificationDTO verificationDTO){
        Optional<VerificationEntity> verificationEntity = verificationRepository.findVerificationEntitiesByCode(verificationDTO.getCode());
        if(verificationEntity.isEmpty()) {
            throw new ValidationException("Верификация провалилась");
        }
        UserEntity userEntity = userRepository.findByMail(verificationDTO.getMail());


    }
}
