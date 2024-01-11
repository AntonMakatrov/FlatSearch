package by.itacademy.user_service.core.exceptions;

public class ValidationException extends IllegalArgumentException{

    public ValidationException() {
        super("Запрос некорректен. Сервер не может обработать запрос");
    }
}
