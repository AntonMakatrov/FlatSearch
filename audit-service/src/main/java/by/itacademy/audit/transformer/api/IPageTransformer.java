package by.itacademy.audit.transformer.api;

import by.itacademy.audit.core.dto.AuditInfoDTO;
import by.itacademy.audit.core.dto.PageOfAuditInfoDTO;
import by.itacademy.audit.core.dto.PageOfReportDTO;
import by.itacademy.audit.core.dto.ReportDTO;
import org.springframework.data.domain.Page;

public interface IPageTransformer {

    PageOfAuditInfoDTO transformPageOfAuditInfoDtoFromPage(Page<AuditInfoDTO> page);

    PageOfReportDTO transformPageOfReportDtoFromPage(Page<ReportDTO> page);

}
