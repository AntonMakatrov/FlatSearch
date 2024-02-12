package by.itacademy.audit.core.entity.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "auditList")
public class XmlAuditList {

    private List<XmlAudit> audits;

    public XmlAuditList() {
    }

    public XmlAuditList(List<XmlAudit> audits) {
        this.audits = audits;
    }

    @XmlElement
    public List<XmlAudit> getAudits() {
        return audits;
    }

    public XmlAuditList setAudits(List<XmlAudit> audits) {
        this.audits = audits;
        return this;
    }
}
