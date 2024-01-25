package by.itacademy.user_service.core.dto;

import by.itacademy.user_service.core.entity.UserRole;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserDetailsDTO implements Identifiable, Userable {

    private UUID id;
    private String mail;
    private String fio;
    private UserRole role;

}
