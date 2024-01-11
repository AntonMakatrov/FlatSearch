package by.itacademy.user_service.service.api;

import by.itacademy.user_service.core.entity.UserEntity;

public interface IVerificationQueueService {
    void add(UserEntity userEntity);
}
