package by.itacademy.audit.repository;

import by.itacademy.audit.core.entity.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<Audit, UUID> {

    @Query("SELECT a FROM Audit AS a WHERE a.userId = :id AND a.actionDate BETWEEN :from AND :to")
    List<Audit> findAllByParam(UUID id, LocalDateTime from, LocalDateTime to);
    Page<Audit> findAll(Pageable pageable);
}
