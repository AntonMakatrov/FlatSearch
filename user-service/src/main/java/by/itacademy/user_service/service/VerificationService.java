package by.itacademy.user_service.service;

import by.itacademy.user_service.core.dto.VerificationDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.entity.VerificationEntity;
import by.itacademy.user_service.core.exceptions.EntityNotFoundException;
import by.itacademy.user_service.core.exceptions.ValidationException;
import by.itacademy.user_service.repository.UserRepository;
import by.itacademy.user_service.repository.VerificationRepository;
import by.itacademy.user_service.service.api.IVerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationService implements IVerificationService {
    private final VerificationRepository verificationRepository;
    public VerificationService(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

    @Override
    public void verify(VerificationDTO verificationDTO){
        Optional<VerificationEntity> verificationEntity = verificationRepository.findVerificationEntitiesByCode(verificationDTO.getCode());
        if(verificationEntity.isEmpty()) {
            throw new ValidationException("Верификация провалилась");
        }
        verificationRepository.deleteEntityByMailAndCode(verificationDTO.getMail(), verificationDTO.getCode());
    }

    @Transactional
    @Override
    public void deleteEntityByMailAndCode(String mail, String code) {
        if (verificationRepository.deleteEntityByMailAndCode(mail, code) == 0) {
            throw new EntityNotFoundException("User", mail);
        }
    }
}
