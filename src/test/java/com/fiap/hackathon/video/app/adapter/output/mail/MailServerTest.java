package com.fiap.hackathon.video.app.adapter.output.mail;

import com.fiap.hackathon.video.app.adapter.output.mail.mail.MailServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MailServerTest {

	private MailServer mailServer;
	private Session session;
	private Transport transport;

	@BeforeEach
	void setUp() {
		mailServer = new MailServer();
		session = mock(Session.class);
		transport = mock(Transport.class);
	}

//	@Test
//	void sendMail_shouldSendEmailWhenParametersAreValid() throws Exception {
//		String email = "test@example.com";
//		Long videoId = 1L;
//
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", "465");
//
//		Session session = Session.getDefaultInstance(props, new Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication("fiaphackathon42@gmail.com", "Fiap321_");
//			}
//		});
//
//		MimeMessage message = new MimeMessage(session);
//		message.setFrom("fiaphackathon42@gmail.com");
//		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
//		message.setSubject("Falha no processamento do vídeo!");
//		message.setText(String.format("O vídeo com id: %s deu problema durante o processamento do mesmo, por favor reenvie o vídeo novamente!", videoId));
//
//		Transport.send(message);
//
//		verify(transport, times(1)).send(message);
//	}

	@Test
	void sendMail_shouldThrowExceptionWhenEmailIsInvalid() {
		String email = "invalid-email";
		Long videoId = 1L;

		assertThrows(RuntimeException.class, () -> mailServer.sendMail(email, videoId));
	}

	@Test
	void sendMail_shouldThrowExceptionWhenVideoIdIsNull() {
		String email = "test@example.com";
		Long videoId = null;

		assertThrows(RuntimeException.class, () -> mailServer.sendMail(email, videoId));
	}
}