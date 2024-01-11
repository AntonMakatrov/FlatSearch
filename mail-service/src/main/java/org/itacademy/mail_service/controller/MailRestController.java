package org.itacademy.mail_service.controller;

import org.itacademy.mail_service.core.dto.MailDTO;
import org.itacademy.mail_service.service.api.IMailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailRestController {
    private final IMailService service;
    public MailRestController(IMailService service){
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody MailDTO mailDTO){
        service.send(mailDTO);
        return ResponseEntity.ok("Письмо отправлено");
    }
}
