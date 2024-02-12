package by.itacademy.user_service.aop;

import by.itacademy.user_service.utils.JwtTokenHandler;
import by.itacademy.user_service.core.dto.*;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.core.entity.UserRole;
import by.itacademy.user_service.core.exceptions.EntityNotFoundException;
import by.itacademy.user_service.httpclients.AuditFeignClient;
import by.itacademy.user_service.repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class AuditedAspect {

    private final UserRepository userRepository;
    private final AuditFeignClient auditFeignClient;
    private final JwtTokenHandler jwtHandler;

    public AuditedAspect(UserRepository userRepository, AuditFeignClient auditFeignClient, JwtTokenHandler jwtHandler) {
        this.userRepository = userRepository;
        this.auditFeignClient = auditFeignClient;
        this.jwtHandler = jwtHandler;
    }

    @Around("@annotation(Audited)")
    public Object checkActivation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Audited annotation = signature.getMethod().getAnnotation(Audited.class);
        Object result = joinPoint.proceed();
        AuditDTO auditDto = buildAuditDto(joinPoint, annotation, result);
        String token = "Bearer " + jwtHandler.generateAccessToken(new UserDetailsDTO().setRole(UserRole.SYSTEM));
        auditFeignClient.sendRequestToCreateLog(token, auditDto);
        return result;
    }

    private AuditDTO buildAuditDto(ProceedingJoinPoint joinPoint, Audited annotation, Object result) {
        switch (annotation.auditedAction()) {
            case REGISTRATION -> {
                return createAuditDto(annotation, (UserEntity) result);
            }
            case VERIFICATION, LOGIN -> {
                return getAuditDtoByEmail(joinPoint, annotation);
            }
            case INFO_ABOUT_ALL_USERS -> {
                return getAuditDtoForInfoAboutAllUsers(annotation);
            }
            case INFO_ABOUT_USER_BY_ID, INFO_ABOUT_ME -> {
                return getAuditDtoForUserInfo(annotation, result);
            }
            case CREATE_USER, UPDATE_USER -> {
                return getAuditDtoForUser(annotation, result);
            }
            default -> throw new RuntimeException("Unrecognized action: " + annotation.auditedAction());
        }
    }

    private AuditDTO getAuditDtoForInfoAboutAllUsers(Audited annotation) {
        UserDetailsDTO userDetailsDto = (UserDetailsDTO) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return createAuditDto(annotation, userDetailsDto, userDetailsDto.getId());
    }

    private AuditDTO getAuditDtoForUserInfo(Audited annotation, Object result) {
        return createAuditDto(annotation, getUserDetailFromSecurityContext(), ((UserDTO) result).getId());
    }

    private AuditDTO getAuditDtoForUser(Audited annotation, Object result) {
        return createAuditDto(annotation, getUserDetailFromSecurityContext(), ((UserEntity) result).getId());
    }

    private UserDetailsDTO getUserDetailFromSecurityContext() {
        return (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private UserEntity findByEmail(String email) {
        return userRepository.findByMail(email).orElseThrow(() -> new EntityNotFoundException("User", email));
    }

    private AuditDTO createAuditDto(Audited annotation, Userable userable) {
        return new AuditDTO().setUserAuditDto(buildUserAuditDto(userable))
                .setAction(annotation.auditedAction())
                .setEssenceType(annotation.essenceType())
                .setEssenceTypeId(userable.getId().toString());
    }

    private AuditDTO createAuditDto(Audited annotation, Userable userable, UUID id) {
        return new AuditDTO().setUserAuditDto(buildUserAuditDto(userable))
                .setAction(annotation.auditedAction())
                .setEssenceType(annotation.essenceType())
                .setEssenceTypeId(id.toString());
    }

    private UserAuditDTO buildUserAuditDto(Userable userable) {
        return new UserAuditDTO().setUserId(userable.getId())
                .setEmail(userable.getMail())
                .setFio(userable.getFio())
                .setUserRole(userable.getRole());
    }

    private AuditDTO getAuditDtoByEmail(ProceedingJoinPoint joinPoint, Audited annotation) {
        Mailable dto = (Mailable) Arrays.stream(joinPoint.getArgs()).toList().get(0);
        return createAuditDto(annotation, findByEmail(dto.getMail()));
    }
}
