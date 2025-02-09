package com.fiap.hackathon.video.app.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Getter
public class EmailConfig {

	@Value("${spring.mail.host}")
	private String HOST;
	@Value("${spring.mail.port}")
	private int PORT;
	@Value("${spring.mail.username}")
	private String USERNAME;
	@Value("${spring.mail.password}")
	private String PASSWORD;
	@Value("${spring.mail.protocol}")
	private String PROTOCOL;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(HOST);
		mailSender.setPort(PORT);

		mailSender.setUsername(USERNAME);
		mailSender.setPassword(PASSWORD);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", PROTOCOL);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}
}
