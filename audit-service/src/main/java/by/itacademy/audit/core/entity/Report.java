package by.itacademy.audit.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(schema = "audit", name = "reports")
public class Report extends BaseEntity {

    @Enumerated(STRING)
    private ReportStatus status;

    @Enumerated(STRING)
    private ReportType type;

    private String description;

    private String userId;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public Report() {
    }

    public Report(
            ReportStatus status,
            ReportType type,
            String description,
            String userId,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        this.status = status;
        this.type = type;
        this.description = description;
        this.userId = userId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Report(ReportStatus status, ReportType type, String userId, LocalDate fromDate, LocalDate toDate) {
        this.status = status;
        this.type = type;
        this.userId = userId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public Report setStatus(ReportStatus status) {
        this.status = status;
        return this;
    }

    public ReportType getType() {
        return type;
    }

    public Report setType(ReportType type) {
        this.type = type;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Report setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public Report setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public Report setToDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Report setDescription(String description) {
        this.description = description;
        return this;
    }
}
