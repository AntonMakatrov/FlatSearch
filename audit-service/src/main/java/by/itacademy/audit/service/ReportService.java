package by.itacademy.audit.service;

import by.itacademy.audit.core.dto.ReportDTO;
import by.itacademy.audit.core.dto.UserActionAuditParamDTO;
import by.itacademy.audit.core.entity.Audit;
import by.itacademy.audit.core.entity.Report;
import by.itacademy.audit.core.entity.ReportStatus;
import by.itacademy.audit.core.entity.ReportType;
import by.itacademy.audit.core.exception.EntityNotFoundException;
import by.itacademy.audit.repository.AuditRepository;
import by.itacademy.audit.repository.ReportRepository;
import by.itacademy.audit.service.api.IFileGenerator;
import by.itacademy.audit.service.api.IReportService;
import by.itacademy.audit.transformer.api.IReportTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static by.itacademy.audit.core.entity.ReportStatus.DONE;
import static by.itacademy.audit.core.entity.ReportStatus.ERROR;


@Slf4j
@Service
public class ReportService implements IReportService {

    private static final String FILE_DIRECTORY = ".";

    private final ReportRepository reportRepository;
    private final AuditRepository auditRepository;
    private final IReportTransformer reportTransformer;
    private final IFileGenerator fileGenerator;

    public ReportService(
            ReportRepository reportRepository,
            AuditRepository auditRepository,
            IReportTransformer reportTransformer,
            @Qualifier("excel-file-generator") IFileGenerator fileGenerator
    ) {
        this.reportRepository = reportRepository;
        this.auditRepository = auditRepository;
        this.reportTransformer = reportTransformer;
        this.fileGenerator = fileGenerator;
    }

    @Override
    @Async
    //@Audited(auditedAction = CREATE_REPORT, essenceType = REPORT)
    public void createReport(ReportType type, UserActionAuditParamDTO paramDto) {
//        if (!auditRepository.existsByUserId(paramDto.getUserId())){
//            throw new EntityNotFoundException("user", UUID.fromString(paramDto.getUserId()));
//        }
        Report reportEntityForSave = UserActionAuditParamDTO.toEntity(type, paramDto);
        Report savedReport = reportRepository.saveAndFlush(reportEntityForSave);

        List<Audit> audits = auditRepository.findAllByParam(
                UUID.fromString(paramDto.getUserId()),
                LocalDateTime.of(paramDto.getFrom(), LocalTime.MIN),
                LocalDateTime.of(paramDto.getTo(), LocalTime.MAX)
        );

        try {
            fileGenerator.generateFile(audits, savedReport.getId().toString());
            savedReport.setStatus(DONE);
        } catch (Exception exception) {
            log.error("Error while generating report" + exception);
            savedReport.setStatus(ERROR);
        }
        reportRepository.save(savedReport);
        log.info("Saved report with status " + savedReport.getStatus());
    }

    @Override
    //@Audited(auditedAction = INFO_ABOUT_ALL_REPORTS, essenceType = REPORT)
    public Page<ReportDTO> getAllReports(Pageable pageable) {
        Page<Report> pageEntity = reportRepository.findAll(pageable);
        List<ReportDTO> reportDtoList = pageEntity.stream()
                .map(reportTransformer::transformReportDtoFromEntity)
                .toList();
        return new PageImpl<ReportDTO>(reportDtoList, pageable, pageEntity.getTotalElements());
    }

    @Override
    //@Audited(auditedAction = SAVE_REPORT, essenceType = REPORT)
    public ResponseEntity<String> saveFileByName(String uuid) throws IOException {
        String fileName = uuid + ".xlsx";
        Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            byte[] fileContent = Files.readAllBytes(filePath);
            String base64Encoded = Base64.getEncoder().encodeToString(fileContent);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            headers.add(
                    HttpHeaders.CONTENT_TYPE,
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            );

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(base64Encoded);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    //@Audited(auditedAction = INFO_ABOUT_ACCESS_REPORT, essenceType = REPORT)
    public ReportStatus getStatusById(String id) {
        try {
            ReportStatus status = ReportStatus.valueOf(reportRepository.getStatusById(UUID.fromString(id)));
            return status;
        } catch (Exception exception) {
            log.info(exception.getMessage());
           throw new EntityNotFoundException("report", UUID.fromString(id));
            }
    }
}
