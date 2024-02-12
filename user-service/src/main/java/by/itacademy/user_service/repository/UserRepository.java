package by.itacademy.user_service.repository;

import by.itacademy.user_service.core.dto.UserDetailsDTO;
import by.itacademy.user_service.core.dto.UserQueryDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Page<UserEntity> findAll(Pageable pageable);

    @Query("SELECT new by.itacademy.user_service.core.dto.UserQueryDTO(u.password, u.status) FROM UserEntity AS u WHERE u.mail = :email")
    Optional<UserQueryDTO> findPasswordAndStatusByEmail(String email);

    @Query("SELECT new by.itacademy.user_service.core.dto.UserDetailsDTO(u.id, u.mail, u.fio, u.role) FROM UserEntity AS u WHERE u.mail = :email")
    Optional<UserDetailsDTO> findIdFioAndRoleByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity AS u SET u.status = :status WHERE u.mail = :email")
    void updateStatusByMail(UserStatus status, String email);

    Optional<UserEntity> findByMail(String email);

    Boolean existsByMail(String email);
}
