package by.itacademy.user_service.service.api;

import by.itacademy.user_service.core.dto.VerificationDTO;

public interface IVerificationService {
    void verify(VerificationDTO verificationDTO);

    void deleteEntityByMailAndCode(String mail, String code);
}
