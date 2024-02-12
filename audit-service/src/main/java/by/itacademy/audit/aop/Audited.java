package by.itacademy.audit.aop;

import by.itacademy.audit.core.entity.AuditedAction;
import by.itacademy.audit.core.entity.EssenceType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audited {

    AuditedAction auditedAction();

    EssenceType essenceType();
}
