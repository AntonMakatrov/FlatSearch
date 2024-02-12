package by.itacademy.audit.controller;

import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.dto.AuditInfoDTO;
import by.itacademy.audit.core.dto.PageOfAuditInfoDTO;
import by.itacademy.audit.service.api.IAuditService;
import by.itacademy.audit.transformer.api.IPageTransformer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/audit")
public class AuditController {

    private final IAuditService auditService;
    private final IPageTransformer pageTransformer;

    public AuditController(IAuditService auditService, IPageTransformer pageTransformer) {
        this.auditService = auditService;
        this.pageTransformer = pageTransformer;
    }

    @GetMapping
    public PageOfAuditInfoDTO getListAudits(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return pageTransformer.transformPageOfAuditInfoDtoFromPage(auditService.getAllAudits(pageable));
    }

    @GetMapping("/{uuid}")
    public AuditInfoDTO getAuditById(@PathVariable UUID uuid) {
        return auditService.findAuditById(uuid);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public AuditDTO acceptRequestToCreateLog(@RequestHeader String AUTHORIZATION, @RequestBody AuditDTO auditDto) {
        return auditService.saveAction(auditDto);
    }
}
