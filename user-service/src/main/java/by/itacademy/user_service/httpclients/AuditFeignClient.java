package by.itacademy.user_service.httpclients;

import by.itacademy.user_service.core.dto.AuditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "audit-logs", url = "${app.feign.audit-logs.url}audit")
public interface AuditFeignClient {

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    AuditDTO sendRequestToCreateLog(
            @RequestHeader String AUTHORIZATION,
            @RequestBody AuditDTO auditDto
    );
}
