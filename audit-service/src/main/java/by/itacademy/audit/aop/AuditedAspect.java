package by.itacademy.audit.aop;

import by.itacademy.audit.controller.utils.JwtTokenHandler;
import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.dto.UserActionAuditParamDTO;
import by.itacademy.audit.core.dto.UserAuditDTO;
import by.itacademy.audit.core.dto.UserDetailsDTO;
import by.itacademy.audit.service.AuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class AuditedAspect {

    private final AuditService auditService;
    private final JwtTokenHandler jwtHandler;

    public AuditedAspect(AuditService auditService, JwtTokenHandler jwtHandler) {
        this.auditService = auditService;
        this.jwtHandler = jwtHandler;
    }

    @Around("@annotation(Audited)")
    public Object checkActivation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Audited annotation = signature.getMethod().getAnnotation(Audited.class);
        Object result = joinPoint.proceed();
        AuditDTO auditDto = buildAuditDto(joinPoint, annotation, result).setId(UUID.randomUUID()).setCreationDate(LocalDateTime.now());
        auditService.saveAction(auditDto);
        return result;
    }

private AuditDTO buildAuditDto(ProceedingJoinPoint joinPoint, Audited annotation, Object result) {
        switch (annotation.auditedAction()) {
            case INFO_ABOUT_ALL_AUDITS, INFO_ABOUT_ALL_REPORTS -> {
              return getAuditDtoForInfoAboutAllReportsAndAudits(annotation);
            }
            case INFO_ABOUT_AUDIT_BY_ID, INFO_ABOUT_ACCESS_REPORT, SAVE_REPORT -> {
                return getAuditDtoForAuditAndReportInfo(annotation, joinPoint);
            }
            case CREATE_REPORT -> {
                return getAuditDtoForCreateReport(annotation, joinPoint);
            }
            default -> throw new RuntimeException("Unrecognized action: " + annotation.auditedAction());
        }
}

    private AuditDTO getAuditDtoForInfoAboutAllReportsAndAudits(Audited annotation) {
        UserDetailsDTO userDetailsDto = getUserDetailFromSecurityContext();
        return createAuditDto(annotation, userDetailsDto, userDetailsDto.getId());
    }

    private AuditDTO getAuditDtoForAuditAndReportInfo(Audited annotation, ProceedingJoinPoint joinPoint) {
        return createAuditDto(annotation,
                getUserDetailFromSecurityContext(),
                UUID.fromString(Arrays.stream(joinPoint.getArgs()).toList().get(0).toString()));
    }

    private AuditDTO getAuditDtoForCreateReport(Audited annotation, ProceedingJoinPoint joinPoint){
        UserActionAuditParamDTO param =(UserActionAuditParamDTO) Arrays.stream(joinPoint.getArgs()).toList().get(1);
        return createAuditDto(
                annotation,
                getUserDetailFromSecurityContext(),
                UUID.fromString(param.getUserId())
        );
    }

    private AuditDTO createAuditDto(Audited annotation, UserDetailsDTO userDetailsDto, UUID id) {
        return new AuditDTO().setUserAuditDto(buildUserAuditDto(userDetailsDto))
                .setAction(annotation.auditedAction())
                .setEssenceType(annotation.essenceType())
                .setEssenceTypeId(id.toString());
    }

    private UserAuditDTO buildUserAuditDto(UserDetailsDTO userDetailsDto) {
        return new UserAuditDTO().setUserId(userDetailsDto.getId())
                .setEmail(userDetailsDto.getEmail())
                .setFio(userDetailsDto.getFio())
                .setUserRole(userDetailsDto.getRole());
    }

    private UserDetailsDTO getUserDetailFromSecurityContext() {
        return (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
