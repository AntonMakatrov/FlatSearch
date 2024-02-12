package by.itacademy.audit.transformer.api;


import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.dto.AuditInfoDTO;
import by.itacademy.audit.core.entity.Audit;

public interface IAuditTransformer {

    AuditInfoDTO transformAuditInfoDtoFromEntity(Audit audit);

    AuditDTO transformAuditDtoFromEntity(Audit audit);

    Audit transformEntityFromAuditDto(AuditDTO auditDto);
}
