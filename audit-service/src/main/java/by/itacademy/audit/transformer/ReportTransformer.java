package by.itacademy.audit.transformer;

import by.itacademy.audit.core.dto.ReportDTO;
import by.itacademy.audit.core.dto.UserActionAuditParamDTO;
import by.itacademy.audit.core.entity.Report;
import by.itacademy.audit.transformer.api.IReportTransformer;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ReportTransformer implements IReportTransformer {

    @Override
    public ReportDTO transformReportDtoFromEntity(Report report) {
        return new ReportDTO().setStatus(report.getStatus())
                .setType(report.getType())
                .setDescription(report.getDescription())
                .setParams(getParamFromEntity(report))
                .setId(report.getId())
                .setCreationDate(report.getCreationDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .setUpdateDate(report.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    private UserActionAuditParamDTO getParamFromEntity(Report report) {
        return new UserActionAuditParamDTO().setUserId(report.getUserId())
                .setFrom(report.getFromDate())
                .setTo(report.getToDate());
    }
}
