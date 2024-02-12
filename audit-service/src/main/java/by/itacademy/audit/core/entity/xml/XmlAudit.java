package by.itacademy.audit.core.entity.xml;


import by.itacademy.audit.core.entity.Audit;
import by.itacademy.audit.core.entity.AuditedAction;
import by.itacademy.audit.core.entity.EssenceType;
import by.itacademy.audit.core.entity.UserRole;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@XmlRootElement(name = "audit")
public class XmlAudit {

    public static XmlAudit from(Audit source) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return new XmlAudit()
                .setAuditId(source.getId())
                .setActionDate(source.getActionDate().format(dateTimeFormatter))
                .setUserId(source.getUserId())
                .setUserEmail(source.getUserEmail())
                .setUserFio(source.getUserFio())
                .setUserRole(source.getUserRole())
                .setAction(source.getAction())
                .setEssenceType(source.getEssenceType())
                .setEssenceTypeId(source.getEssenceTypeId());
    }

    private UUID auditId;

    private String actionDate;

    private UUID userId;

    private String userEmail;

    private String userFio;

    private UserRole userRole;

    private AuditedAction action;

    private EssenceType essenceType;

    private String essenceTypeId;

    public XmlAudit() {
    }

    public XmlAudit(UUID auditId,
                    String actionDate,
                    UUID userId,
                    String userEmail,
                    String userFio,
                    UserRole userRole,
                    AuditedAction action,
                    EssenceType essenceType,
                    String essenceTypeId) {
        this.auditId = auditId;
        this.actionDate = actionDate;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userFio = userFio;
        this.userRole = userRole;
        this.action = action;
        this.essenceType = essenceType;
        this.essenceTypeId = essenceTypeId;
    }

    @XmlElement
    public UUID getAuditId() {
        return auditId;
    }

    public XmlAudit setAuditId(UUID auditId) {
        this.auditId = auditId;
        return this;
    }

    @XmlElement
    public String getActionDate() {
        return actionDate;
    }

    public XmlAudit setActionDate(String actionDate) {
        this.actionDate = actionDate;
        return this;
    }

    @XmlElement
    public UUID getUserId() {
        return userId;
    }

    public XmlAudit setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    @XmlElement
    public String getUserEmail() {
        return userEmail;
    }

    public XmlAudit setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    @XmlElement
    public String getUserFio() {
        return userFio;
    }

    public XmlAudit setUserFio(String userFio) {
        this.userFio = userFio;
        return this;
    }

    @XmlElement
    public UserRole getUserRole() {
        return userRole;
    }

    public XmlAudit setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    @XmlElement
    public AuditedAction getAction() {
        return action;
    }

    public XmlAudit setAction(AuditedAction action) {
        this.action = action;
        return this;
    }

    @XmlElement
    public EssenceType getEssenceType() {
        return essenceType;
    }

    public XmlAudit setEssenceType(EssenceType essenceType) {
        this.essenceType = essenceType;
        return this;
    }

    @XmlElement
    public String getEssenceTypeId() {
        return essenceTypeId;
    }

    public XmlAudit setEssenceTypeId(String essenceTypeId) {
        this.essenceTypeId = essenceTypeId;
        return this;
    }


}
