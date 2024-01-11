package by.itacademy.user_service.core.exceptions.body;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorDetails {

    private String field;

    private String message;

}
