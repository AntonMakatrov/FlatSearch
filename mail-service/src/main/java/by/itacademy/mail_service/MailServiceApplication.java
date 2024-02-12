package by.itacademy.mail_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MailServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MailServiceApplication.class, args);
	}
}
