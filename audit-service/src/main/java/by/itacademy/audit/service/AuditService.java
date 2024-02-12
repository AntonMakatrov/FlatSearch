package by.itacademy.audit.service;

import by.itacademy.audit.aop.Audited;
import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.dto.AuditInfoDTO;
import by.itacademy.audit.core.entity.Audit;
import by.itacademy.audit.core.exception.EntityNotFoundException;
import by.itacademy.audit.repository.AuditRepository;
import by.itacademy.audit.service.api.IAuditService;
import by.itacademy.audit.transformer.AuditTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static by.itacademy.audit.core.entity.AuditedAction.INFO_ABOUT_ALL_AUDITS;
import static by.itacademy.audit.core.entity.AuditedAction.INFO_ABOUT_AUDIT_BY_ID;
import static by.itacademy.audit.core.entity.EssenceType.AUDIT;


@Service
public class AuditService implements IAuditService {

    private final AuditRepository auditRepository;
    private final AuditTransformer auditTransformer;

    public AuditService(AuditRepository auditRepository, AuditTransformer auditTransformer) {
        this.auditRepository = auditRepository;
        this.auditTransformer = auditTransformer;
    }

    @Override
    //@Audited(auditedAction = INFO_ABOUT_ALL_AUDITS, essenceType = AUDIT)
    public Page<AuditInfoDTO> getAllAudits(Pageable pageable) {
        Page<Audit> pageEntity = auditRepository.findAll(pageable);
        List<AuditInfoDTO> auditInfoDtoList = pageEntity.stream()
                .map(auditTransformer::transformAuditInfoDtoFromEntity)
                .toList();
        return new PageImpl<>(auditInfoDtoList, pageable, pageEntity.getTotalElements());
    }

    @Override
    //@Audited(auditedAction = INFO_ABOUT_AUDIT_BY_ID, essenceType = AUDIT)
    public AuditInfoDTO findAuditById(UUID id) {
        Audit auditById = auditRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Audit", id));
        return auditTransformer.transformAuditInfoDtoFromEntity(auditById);
    }

    @Override
    public AuditDTO saveAction(AuditDTO auditDto) {
        Audit entity = auditRepository.save(auditTransformer.transformEntityFromAuditDto(auditDto));
        return auditTransformer.transformAuditDtoFromEntity(entity);
    }
}
