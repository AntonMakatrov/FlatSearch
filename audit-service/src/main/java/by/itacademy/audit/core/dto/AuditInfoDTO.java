package by.itacademy.audit.core.dto;

import by.itacademy.audit.core.entity.EssenceType;
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
public class AuditInfoDTO {

    @JsonProperty("uuid")
    private UUID id;

    @JsonProperty("dt_create")
    private Long creationDate;

    @JsonProperty("user")
    private UserAuditDTO userAuditDto;

    @JsonProperty("text")
    private String action;

    @JsonProperty("type")
    private EssenceType essenceType;

    @JsonProperty("id")
    private String essenceTypeId;
}
