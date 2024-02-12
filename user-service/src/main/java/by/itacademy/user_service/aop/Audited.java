package by.itacademy.user_service.aop;

import by.itacademy.user_service.core.entity.AuditedAction;
import by.itacademy.user_service.core.entity.EssenceType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audited {

    AuditedAction auditedAction();

    EssenceType essenceType();
}
