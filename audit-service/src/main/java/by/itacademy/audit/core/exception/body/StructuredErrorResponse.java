package by.itacademy.audit.core.exception.body;

import java.util.List;

public class StructuredErrorResponse {
    private String logref;
    private List<ErrorField> errors;

    public StructuredErrorResponse() {
    }

    public StructuredErrorResponse(String logref, List<ErrorField> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public String getLogref() {
        return logref;
    }

    public List<ErrorField> getErrors() {
        return errors;
    }
}
