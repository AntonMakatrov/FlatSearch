package by.itacademy.user_service.core.exceptions;

public class ValidationException extends IllegalArgumentException{

    public ValidationException(String message) {
        super(message);
    }
}
