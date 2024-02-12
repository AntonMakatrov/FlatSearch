package by.itacademy.audit.controller;

import by.itacademy.audit.core.dto.PageOfReportDTO;
import by.itacademy.audit.core.dto.UserActionAuditParamDTO;
import by.itacademy.audit.core.entity.ReportType;
import by.itacademy.audit.service.api.IReportService;
import by.itacademy.audit.transformer.api.IPageTransformer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/report")
public class ReportController {

    private final IReportService reportService;
    private final IPageTransformer pageTransformer;

    public ReportController(IReportService reportService, IPageTransformer pageTransformer) {
        this.reportService = reportService;
        this.pageTransformer = pageTransformer;
    }

    @PostMapping(value = "/{type}")
    public ResponseEntity<String> startReport(@PathVariable ReportType type,
                            @Validated @RequestBody UserActionAuditParamDTO paramDto) {
        reportService.createReport(type, paramDto);
        return new ResponseEntity<>("Отчёт запущен", HttpStatus.CREATED);
    }

    @GetMapping
    public PageOfReportDTO getListReports(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return pageTransformer.transformPageOfReportDtoFromPage(reportService.getAllReports(pageable));
    }

    @GetMapping(value = "/{uuid}/export")
    public ResponseEntity<String> saveFile(@PathVariable (name = "uuid") String uuid) throws IOException{
        return reportService.saveFileByName(uuid);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/{uuid}/export")
    public ResponseEntity getReportStatus(@PathVariable String uuid) {
        return switch (reportService.getStatusById(uuid)) {
            case DONE -> ResponseEntity.status(200).build();
            case ERROR, LOADED, PROGRESS -> ResponseEntity.status(505).build();
        };
    }
}
