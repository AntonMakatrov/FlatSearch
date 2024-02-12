package by.itacademy.user_service.repository;

import by.itacademy.user_service.core.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, UUID> {
    Optional<VerificationEntity> findVerificationEntitiesByCode(String code);
    Optional<VerificationEntity> findFirstBySendCodeFalse();
    @Modifying
    @Transactional
    @Query("DELETE FROM VerificationEntity AS vrf WHERE vrf.mail = :mail AND vrf.code = :code")
    Integer deleteEntityByMailAndCode(String mail, String code);
}
