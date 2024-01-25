package by.itacademy.user_service.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserLoginDTO implements Mailable {

    @Email(message = "Email should be valid")
    @NotNull
    private String mail;

    @NotNull
    @Size(min = 6, max = 12)
    private String password;
}
