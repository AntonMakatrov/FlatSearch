package by.itacademy.user_service.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @Email(message = "Email should be valid")
    @NotNull
    private String mail;

    private String fio;

    @NotNull
    @Size(min = 6, max = 12)
    private String password;
}
