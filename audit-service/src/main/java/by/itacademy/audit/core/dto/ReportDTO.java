package by.itacademy.audit.core.dto;

import by.itacademy.audit.core.entity.ReportStatus;
import by.itacademy.audit.core.entity.ReportType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ReportDTO {

    @JsonProperty("uuid")
    private UUID id;

    @JsonProperty("dt_create")
    private Long creationDate;

    @JsonProperty("dt_update")
    private Long updateDate;

    private ReportStatus status;
    private ReportType type;
    private String description;
    private UserActionAuditParamDTO params;
}
