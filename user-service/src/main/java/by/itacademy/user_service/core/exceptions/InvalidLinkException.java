package by.itacademy.user_service.core.exceptions;

public class InvalidLinkException extends RuntimeException {

    public InvalidLinkException() {
        super("Ссылка устарела или не действительна");
    }
}
