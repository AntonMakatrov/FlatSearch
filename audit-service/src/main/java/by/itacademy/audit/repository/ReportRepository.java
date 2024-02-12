package by.itacademy.audit.repository;

import by.itacademy.audit.core.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    @Query("SELECT r.status FROM Report AS r WHERE r.id = :id")
    String getStatusById(UUID id);

    Boolean existsByUserId(String userId);
}
