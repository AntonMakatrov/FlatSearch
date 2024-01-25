package by.itacademy.user_service.service.api;

import by.itacademy.user_service.core.dto.UserCreateDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.core.dto.UserDetailsDTO;
import by.itacademy.user_service.core.dto.UserQueryDto;
import by.itacademy.user_service.core.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    UserDTO findInfoAbout();

    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO findById(UUID id);

    void save(UserCreateDTO user);
    UserEntity createUser(UserCreateDTO userCreationDto);

    UserEntity update(UserCreateDTO user, UUID id, LocalDateTime dt_update);

    void activate(String mail);

    UserDetailsDTO getUserDetailsDto(String mail);

    UserQueryDto getUserQueryDto(String email);

}
