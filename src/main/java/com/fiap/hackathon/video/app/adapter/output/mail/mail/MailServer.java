package com.fiap.hackathon.video.app.adapter.output.mail.mail;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServer {

	public void sendMail(String email, Long videoId) {
		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication("fiaphackathon42@gmail.com",
								"Fiap321_");
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("fiaphackathon42@gmail.com"));

			Address[] toUser = InternetAddress.parse(email);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Falha no processamento do vídeo!");
			message.setText(String.format("O vídeo com id: %s deu problema durante o processamento do mesmo, por favor reenvie o vídeo novamente!", videoId));

			Transport.send(message);
			System.out.println("E-mail enviado!!!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
