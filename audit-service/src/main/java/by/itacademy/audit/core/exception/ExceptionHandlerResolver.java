package by.itacademy.audit.core.exception;

import by.itacademy.audit.core.exception.body.ErrorField;
import by.itacademy.audit.core.exception.body.ErrorResponse;
import by.itacademy.audit.core.exception.body.StructuredErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ResponseBody
@RestControllerAdvice
public class ExceptionHandlerResolver {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFound(EntityNotFoundException exception) {
        return new ErrorResponse("error", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHandlerResolverException(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage());
        return new ErrorResponse("error",
                exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleValidationException(ValidationException exception) {
        log.error(exception.getMessage());
        return new ErrorResponse("error",
                exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public StructuredErrorResponse handleValidation(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
        return new StructuredErrorResponse(
                "structured_error",
                exception.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(this::convert)
                        .toList()
        );
    }

    private ErrorField convert(ObjectError objectError) {
        return new ErrorField(
                ((FieldError) objectError).getField(),
                objectError.getDefaultMessage()
        );
    }
}
