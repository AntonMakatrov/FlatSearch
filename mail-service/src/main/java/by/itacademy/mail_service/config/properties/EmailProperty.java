package by.itacademy.mail_service.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Data
@ConfigurationProperties(prefix = "app.mail")
public class EmailProperty {

    private String userName;
    private String password;

    @Bean
    public JavaMailSender javamailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("antonmakatrau@gmail.com");
        javaMailSender.setPassword("vgro mulv ljqc fydo");

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

}
