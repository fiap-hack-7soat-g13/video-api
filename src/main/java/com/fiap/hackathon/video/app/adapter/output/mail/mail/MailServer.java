package com.fiap.hackathon.video.app.adapter.output.mail.mail;

import com.fiap.hackathon.video.core.domain.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class MailServer {

	private static final String EMAIL_TEMPLATE = "email-template";

	private JavaMailSender mailSender;
	private SpringTemplateEngine templateEngine;

	public void sendMessage(Mail mail) throws MessagingException {
		this.mailSender.send(this.buildMimeMessage(mail));
	}

	private MimeMessage buildMimeMessage(Mail mail) throws MessagingException {
		MimeMessage message = this.mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		helper.setTo(mail.getTo());
		helper.setText(this.getHtmlContent(mail), true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());

		return message;
	}

	private String getHtmlContent(Mail mail) {
		org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
		context.setVariables(mail.getModel());
		String html = this.templateEngine.process(EMAIL_TEMPLATE, context);

		return html;
	}

}
