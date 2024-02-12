package by.itacademy.audit.service.api;

import by.itacademy.audit.core.dto.ReportDTO;
import by.itacademy.audit.core.dto.UserActionAuditParamDTO;
import by.itacademy.audit.core.entity.ReportStatus;
import by.itacademy.audit.core.entity.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface IReportService {

    void createReport(ReportType type, UserActionAuditParamDTO paramDto);

    Page<ReportDTO> getAllReports(Pageable pageable);

    ResponseEntity<String> saveFileByName(String uuid) throws IOException;

    ReportStatus getStatusById(String id);
}
