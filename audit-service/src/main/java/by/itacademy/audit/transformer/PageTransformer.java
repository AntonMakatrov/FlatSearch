package by.itacademy.audit.transformer;

import by.itacademy.audit.core.dto.AuditInfoDTO;
import by.itacademy.audit.core.dto.PageOfAuditInfoDTO;
import by.itacademy.audit.core.dto.PageOfReportDTO;
import by.itacademy.audit.core.dto.ReportDTO;
import by.itacademy.audit.transformer.api.IPageTransformer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageTransformer implements IPageTransformer {

    @Override
    public PageOfAuditInfoDTO transformPageOfAuditInfoDtoFromPage(Page<AuditInfoDTO> page) {
        return (PageOfAuditInfoDTO) new PageOfAuditInfoDTO()
                .setContent(page.getContent())
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast());
    }

    @Override
    public PageOfReportDTO transformPageOfReportDtoFromPage(Page<ReportDTO> page) {
        return (PageOfReportDTO) new PageOfReportDTO()
                .setContent(page.getContent())
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast());
    }
}
