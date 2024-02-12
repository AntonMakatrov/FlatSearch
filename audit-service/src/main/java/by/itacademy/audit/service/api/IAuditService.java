package by.itacademy.audit.service.api;

import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.dto.AuditInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAuditService {

    Page<AuditInfoDTO> getAllAudits(Pageable pageable);

    AuditInfoDTO findAuditById(UUID id);

    AuditDTO saveAction(AuditDTO auditDto);
}
