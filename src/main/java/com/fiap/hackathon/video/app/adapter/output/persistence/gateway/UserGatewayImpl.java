package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.app.adapter.output.mail.mail.MailServer;
import com.fiap.hackathon.video.core.domain.Mail;
import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.gateway.UserGateway;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UserGatewayImpl implements UserGateway, ReactiveUserDetailsService {

	private final MailServer mailServer;

	@Override
	public Mono<User> getUserByEmail(String email, String username, Long id) {
		return Mono.justOrEmpty(User.builder().email(email).id(id).username(username).build());
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		try {
			return Mono.justOrEmpty((UserDetails) getUserByEmail(username, "", 0L).block());
		} catch (Exception e) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
	}

	@Override
	public void sendMmail(String email, Long videoId) throws MessagingException {
		Mail mail = new Mail();
		mail.setFrom("fiaphackathon42@gmail.com");
		mail.setTo(email);
		mail.setSubject("Falha no processamento do vídeo!");
		mail.setContent(String.format("O vídeo com id: %s deu problema durante o processamento do mesmo, por favor reenvie o vídeo novamente!", videoId));

		mailServer.sendMessage(mail);
	}

}
