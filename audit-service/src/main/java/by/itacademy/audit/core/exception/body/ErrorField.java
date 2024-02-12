package by.itacademy.audit.core.exception.body;

public class ErrorField {
    private String field;
    private String message;

    public ErrorField() {
    }

    public ErrorField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
