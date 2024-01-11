package by.itacademy.user_service.service;

import by.itacademy.user_service.core.dto.VerificationDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.entity.VerificationEntity;
import by.itacademy.user_service.core.exceptions.InternalServerErrorException;
import by.itacademy.user_service.repository.VerificationRepository;
import by.itacademy.user_service.service.api.ISendVerificationService;
import by.itacademy.user_service.service.api.IVerificationQueueService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SendVerificationService implements IVerificationQueueService {
    private final VerificationRepository verificationRepository;
    private final ISendVerificationService sendVerificationService;
    private final ModelMapper modelMapper;

    public SendVerificationService(VerificationRepository verificationRepository, ISendVerificationService sendVerificationService, ModelMapper modelMapper) {
        this.verificationRepository = verificationRepository;
        this.sendVerificationService = sendVerificationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(UserEntity userEntity) {
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setId(UUID.randomUUID());
        verificationEntity.setCode(UUID.randomUUID().toString());
        verificationEntity.setMail(userEntity.getMail());
        verificationEntity.setSendCode(false);

        try {
            verificationRepository.save(verificationEntity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    @Scheduled(fixedRate = 10000)
    private void sendCode() {
        Optional<VerificationEntity> verificationEntity = verificationRepository.findFirstBySendCodeFalse();

        if(verificationEntity.isPresent()) {
            VerificationDTO verificationDTO = convertToDto(verificationEntity.get());
            sendVerificationService.send(verificationDTO);
            verificationEntity.get().setSendCode(true);

            try {
                verificationRepository.save(verificationEntity.get());
            } catch (DataAccessException e) {
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    private VerificationDTO convertToDto(VerificationEntity verificationEntity) {
        return modelMapper.map(verificationEntity, VerificationDTO.class);
    }
}
