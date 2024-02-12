package by.itacademy.audit.transformer.api;


import by.itacademy.audit.core.dto.ReportDTO;
import by.itacademy.audit.core.entity.Report;

public interface IReportTransformer {

    ReportDTO transformReportDtoFromEntity(Report report);
}
