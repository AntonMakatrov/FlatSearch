package by.itacademy.user_service.core.entity;

import by.itacademy.user_service.core.dto.Userable;
import jakarta.persistence.*;

@Entity
@Table(schema = "users", name = "users")
public class UserEntity extends BaseEntity implements Userable {
    @Column(name = "mail")
    private String mail;
    @Column(name = "fio")
    private String fio;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Column(name = "password")
    private String password;

    public UserEntity() {
    }

    @Override
    public String getMail() {
        return mail;
    }

    public UserEntity setMail(String mail) {
        this.mail = mail;
        return this;
    }

    @Override
    public String getFio() {
        return fio;
    }

    public UserEntity setFio(String fio) {
        this.fio = fio;
        return this;
    }

    @Override
    public UserRole getRole() {
        return role;
    }

    public UserEntity setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserEntity setStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }
}
