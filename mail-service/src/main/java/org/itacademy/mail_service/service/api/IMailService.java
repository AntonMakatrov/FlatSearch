package org.itacademy.mail_service.service.api;

import org.itacademy.mail_service.core.dto.MailDTO;

public interface IMailService {
    void send(MailDTO mailDTO);
}
