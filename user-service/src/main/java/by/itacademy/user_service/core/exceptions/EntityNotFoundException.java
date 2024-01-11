package by.itacademy.user_service.core.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, UUID id) {
        super(entityName + " not found by id " + id);
    }

    public EntityNotFoundException(String entityName, String email) {
        super(entityName + " not found by email " + email);
    }
}
