package by.itacademy.audit.core.exception.body;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private String logref;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public String getMessage() {
        return message;
    }
}
