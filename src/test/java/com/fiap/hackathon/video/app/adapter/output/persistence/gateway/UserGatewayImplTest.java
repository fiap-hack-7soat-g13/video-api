package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.app.adapter.output.mail.mail.MailServer;
import com.fiap.hackathon.video.core.domain.Mail;
import com.fiap.hackathon.video.core.domain.User;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserGatewayImplTest {

	private UserGatewayImpl userGateway;
	private MailServer mailServer;

	@BeforeEach
	void setUp() {
		mailServer = mock(MailServer.class);
		userGateway = new UserGatewayImpl(mailServer);
	}

	@Test
	void getUserByEmail_shouldReturnUserWhenAllParametersAreValid() {
		String email = "user@example.com";
		String username = "username";
		Long id = 1L;

		Mono<User> result = this.userGateway.getUserByEmail(email, username, id);

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getEmail().equals(email) && user.getUsername().equals(username) && user.getId().equals(id))
				.verifyComplete();
	}

	@Test
	void getUserByEmail_shouldReturnEmptyWhenAllParametersAreNull() {
		Mono<User> result = this.userGateway.getUserByEmail(null, null, null);

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getEmail() == null && user.getUsername() == null && user.getId() == null)
				.verifyComplete();
	}

	@Test
	void findByUsername_shouldReturnUserDetailsWhenUsernameIsValid() {
		String username = "username";

		Mono<UserDetails> result = this.userGateway.findByUsername(username);

		StepVerifier.create(result)
				.expectNextMatches(userDetails -> userDetails.getUsername().equals(username));
	}

	@Test
	void sendMmail_shouldSucess() throws MessagingException {
		String email = "test@example.com";
		Long videoId = 1L;
		doNothing().when(this.mailServer).sendMessage(any(Mail.class));

		this.userGateway.sendMmail(email, videoId);

		verify(this.mailServer).sendMessage(any(Mail.class));
	}

}