package by.itacademy.audit.transformer;

import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.dto.AuditInfoDTO;
import by.itacademy.audit.core.dto.UserAuditDTO;
import by.itacademy.audit.core.entity.Audit;
import by.itacademy.audit.transformer.api.IAuditTransformer;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class AuditTransformer implements IAuditTransformer {

    @Override
    public AuditInfoDTO transformAuditInfoDtoFromEntity(Audit audit) {
        return new AuditInfoDTO().setId(audit.getId())
                .setCreationDate(audit.getActionDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .setUserAuditDto(getUserDtoFromAudit(audit))
                .setAction(audit.getAction().getDescription())
                .setEssenceType(audit.getEssenceType())
                .setEssenceTypeId(audit.getEssenceTypeId());
    }

    @Override
    public AuditDTO transformAuditDtoFromEntity(Audit audit) {
        return new AuditDTO().setId(audit.getId())
                .setCreationDate(audit.getActionDate())
                .setUserAuditDto(getUserDtoFromAudit(audit))
                .setAction(audit.getAction())
                .setEssenceType(audit.getEssenceType())
                .setEssenceTypeId(audit.getEssenceTypeId());
    }

    @Override
    public Audit transformEntityFromAuditDto(AuditDTO auditDto) {
        return new Audit().setId(auditDto.getId())
                .setActionDate(auditDto.getCreationDate())
                .setUserId(auditDto.getUserAuditDto().getUserId())
                .setUserEmail(auditDto.getUserAuditDto().getEmail())
                .setUserFio(auditDto.getUserAuditDto().getFio())
                .setUserRole(auditDto.getUserAuditDto().getUserRole())
                .setAction(auditDto.getAction())
                .setEssenceType(auditDto.getEssenceType())
                .setEssenceTypeId(auditDto.getEssenceTypeId());
    }

    private UserAuditDTO getUserDtoFromAudit(Audit audit) {
        return new UserAuditDTO().setUserId(audit.getUserId())
                .setEmail(audit.getUserEmail())
                .setFio(audit.getUserFio())
                .setUserRole(audit.getUserRole());
    }
}
