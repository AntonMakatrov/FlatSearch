package by.itacademy.user_service.core.exceptions.body;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorResponse implements Serializable {

    private String logRef;

    private String message;

}
