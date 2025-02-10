package com.fiap.hackathon.video.app.adapter.output.mail;

import com.fiap.hackathon.video.app.adapter.output.mail.mail.MailServer;
import com.fiap.hackathon.video.core.domain.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class MailServerTest {

	private JavaMailSender mailSender;
	private SpringTemplateEngine templateEngine;
	private MailServer mailServer;

	@BeforeEach
	void setUp() {
		mailSender = mock(JavaMailSender.class);
		templateEngine = mock(SpringTemplateEngine.class);
		mailServer = new MailServer(mailSender, templateEngine);
	}

	@Test
	void sendMessage_shouldSendEmailWhenMailIsValid() throws MessagingException {
		Mail mail = new Mail("from@example.com", "to@example.com", "Subject", "Conteúdo", new HashMap<>());

		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(this.mailSender.createMimeMessage()).thenReturn(mimeMessage);
		when(this.templateEngine.process(anyString(), any(Context.class))).thenReturn("<html></html>");

		mailServer.sendMessage(mail);

		verify(this.mailSender).send(any(jakarta.mail.internet.MimeMessage.class));
	}
}