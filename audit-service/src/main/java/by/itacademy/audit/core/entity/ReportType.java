package by.itacademy.audit.core.entity;

public enum ReportType {

    JOURNAL_AUDIT("Журнал аудита");

    private String description;

    ReportType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
