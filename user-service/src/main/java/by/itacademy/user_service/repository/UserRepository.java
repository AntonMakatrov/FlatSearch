package by.itacademy.user_service.repository;

import by.itacademy.user_service.core.dto.UserDetailsDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByMail(String mail);
    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserDetailsDTO> findIdFioAndRoleByMail(String email);
}
