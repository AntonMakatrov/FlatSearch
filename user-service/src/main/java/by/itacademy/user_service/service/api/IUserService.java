package by.itacademy.user_service.service.api;

import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.core.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    UserDTO findInfoAbout();

    Page<UserDTO> getAllUsers(Pageable pageable);

    Optional<UserEntity> findById(UUID id);

    void save(UserEntity user);

    void update(UserEntity user, UUID id, LocalDateTime dt_update);

    void activate(String mail);

}
