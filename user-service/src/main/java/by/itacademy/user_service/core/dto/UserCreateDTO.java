package by.itacademy.user_service.core.dto;

import by.itacademy.user_service.core.entity.UserRole;
import by.itacademy.user_service.core.entity.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserCreateDTO {

    @Email(message = "Email should be valid")
    @NotNull
    private String mail;

    private String fio;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;

    @NotNull
    @Size(min = 6, max = 12)
    private String password;
}
