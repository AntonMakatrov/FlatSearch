package by.itacademy.user_service.core.entity;

public enum UserStatus {

    WAITING_ACTIVATION("WAITING_ACTIVATION"),
    ACTIVATED("ACTIVATED"),
    DEACTIVATED("DEACTIVATED");

    private final String statusId;

    UserStatus(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusId() {
        return statusId;
    }
}
