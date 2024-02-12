package by.itacademy.audit.core.dto;

import by.itacademy.audit.core.entity.Report;
import by.itacademy.audit.core.entity.ReportType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

import static by.itacademy.audit.core.entity.ReportStatus.PROGRESS;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserActionAuditParamDTO {

    @JsonProperty("user")
    @NotNull
    private String userId;

    @NotNull
    @JsonFormat(pattern = "yyyy.MM.dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate from;

    @NotNull
    @JsonFormat(pattern = "yyyy.MM.dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate to;

    public static Report toEntity(ReportType type, UserActionAuditParamDTO dto) {
        return new Report()
                .setStatus(PROGRESS)
                .setType(type)
                .setUserId(dto.getUserId())
                .setFromDate(dto.getFrom())
                .setToDate(dto.getTo())
                .setDescription(type.getDescription() + " from " + dto.getFrom() + " to " + dto.getTo());
    }
}
