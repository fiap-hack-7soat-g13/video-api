package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.app.adapter.output.mail.mail.MailServer;
import com.fiap.hackathon.video.core.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class UserGatewayImplTest {

	private UserGatewayImpl userGateway;
	private MailServer mailServer;

	@BeforeEach
	void setUp() {
		mailServer = mock(MailServer.class);
		userGateway = new UserGatewayImpl(mailServer);
	}

	@Test
	void getUserByEmail_shouldHandleEmptyEmail() {
		String email = "";
		String username = "username";
		Long id = 1L;

		Mono<User> result = userGateway.getUserByEmail(email, username, id);

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getEmail().equals(email) && user.getUsername().equals(username) && user.getId().equals(id))
				.verifyComplete();
	}

	@Test
	void getUserByEmail_shouldHandleEmptyUsername() {
		String email = "user@example.com";
		String username = "";
		Long id = 1L;

		Mono<User> result = userGateway.getUserByEmail(email, username, id);

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getEmail().equals(email) && user.getUsername().equals(username) && user.getId().equals(id))
				.verifyComplete();
	}

	@Test
	void getUserByEmail_shouldHandleEmptyId() {
		String email = "user@example.com";
		String username = "username";
		Long id = 0L;

		Mono<User> result = userGateway.getUserByEmail(email, username, id);

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getEmail().equals(email) && user.getUsername().equals(username) && user.getId().equals(id))
				.verifyComplete();
	}

}