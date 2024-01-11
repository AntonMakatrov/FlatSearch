package by.itacademy.user_service.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "users", name = "codes")
public class VerificationEntity {
    @Id
    private UUID id;
    private String code;
    private String mail;
    @Column(name = "send_code")
    private boolean sendCode;

    public VerificationEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isSendCode() {
        return sendCode;
    }

    public void setSendCode(boolean sendCode) {
        this.sendCode = sendCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationEntity that = (VerificationEntity) o;
        return sendCode == that.sendCode && Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, mail, sendCode);
    }

    @Override
    public String toString() {
        return "VerificationEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", mail='" + mail + '\'' +
                ", sendCode=" + sendCode +
                '}';
    }
}
