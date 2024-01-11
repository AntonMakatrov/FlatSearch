package by.itacademy.user_service.core.entity;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER"),
    MANAGER("MANAGER"),
    SYSTEM("SYSTEM");

    private final String roleId;

    UserRole(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }
}
