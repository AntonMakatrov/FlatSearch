package by.itacademy.user_service.core.exceptions.body;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StructuredErrorResponse {

    private String logref;

    private List<ErrorDetails> errors;
}
