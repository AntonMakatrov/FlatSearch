package by.itacademy.user_service.core.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    @Id
    private UUID id = UUID.randomUUID();
    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime creationDate;

    @Version
    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updateDate;

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
}
