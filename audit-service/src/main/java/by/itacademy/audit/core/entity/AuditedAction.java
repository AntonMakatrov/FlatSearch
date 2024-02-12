package by.itacademy.audit.core.entity;

public enum AuditedAction {

    REGISTRATION("Регистрация пройдена"),
    VERIFICATION("Верификация пройдена"),
    LOGIN("Авторизация пройдена"),
    INFO_ABOUT_ME("Информация о себе"),
    UPDATE_PASSWORD("пользователь обновил пароль"),
    INFO_ABOUT_ALL_USERS("Информация о всех пользователях"),
    INFO_ABOUT_USER_BY_ID("Информация о юзере по индентификатору"),
    CREATE_USER("Создан пользователь"),
    UPDATE_USER("Пользователь обновлен"),
    INFO_ABOUT_ALL_AUDITS("Информация о всех аудитах"),
    INFO_ABOUT_AUDIT_BY_ID("Информация об аудите по индентификатору"),
    INFO_ABOUT_ALL_REPORTS("Информация о всех отчетах"),
    CREATE_REPORT("Создан отчет"),
    INFO_ABOUT_ACCESS_REPORT("Информация о доступности скачивания отчета"),
    SAVE_REPORT("Скачивание отчета");

    private String description;

    AuditedAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
