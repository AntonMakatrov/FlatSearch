package org.itacademy.mail_service.service;

import org.itacademy.mail_service.core.dto.MailDTO;
import org.itacademy.mail_service.service.api.IMailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService {
    private JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void send(MailDTO mailDTO) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(
                "Код верификации: " + mailDTO.getCode() +
                " или перейдите по ссылке http://localhost:8080/users/verification?code="
                + mailDTO.getCode() + "&mail=" + mailDTO.getMail());
        mailMessage.setTo(mailDTO.getMail());
        this.mailSender.send(mailMessage);
    }
}
