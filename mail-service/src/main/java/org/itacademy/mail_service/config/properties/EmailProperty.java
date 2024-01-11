package org.itacademy.mail_service.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Data
@ConfigurationProperties(prefix = "spring.mail")
public class EmailProperty {
    private String host;
    private int port;
    private String userName;
    private String password;

    @Bean
    public JavaMailSender javamailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(password);

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");


        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

}
