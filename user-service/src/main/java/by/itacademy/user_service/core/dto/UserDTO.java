package by.itacademy.user_service.core.dto;

import by.itacademy.user_service.core.entity.UserRole;
import by.itacademy.user_service.core.entity.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDTO implements Identifiable{
    @JsonProperty("uuid")
    private UUID id;

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

    @JsonProperty("dt_create")
    private Long dt_create;

    @JsonProperty("dt_update")
    private Long dt_update;}
