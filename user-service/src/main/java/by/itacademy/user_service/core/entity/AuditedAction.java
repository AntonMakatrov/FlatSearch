package by.itacademy.user_service.core.entity;

public enum AuditedAction {
    REGISTRATION("пройден процесс регистрации"),
    VERIFICATION("пройден процесс верификации"),
    LOGIN("пройден процесс авторизации"),
    INFO_ABOUT_ME("запрошена информация о себе"),
    INFO_ABOUT_ALL_USERS("запрошена информация о всех пользователях"),
    INFO_ABOUT_USER_BY_ID("запрошена информация о юзере по индентификатору"),
    CREATE_USER("создан юзер"),
    UPDATE_USER("юзер обновлен");

    private String description;

    AuditedAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
