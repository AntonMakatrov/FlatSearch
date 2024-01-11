package by.itacademy.user_service.core.dto;

import by.itacademy.user_service.core.entity.UserRole;

import java.util.UUID;

public interface Userable extends Identifiable {
    UUID getId();

    String getMail();

    String getFio();

    UserRole getRole();
}
