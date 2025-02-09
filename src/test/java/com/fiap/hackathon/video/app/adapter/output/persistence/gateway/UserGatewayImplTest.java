package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.core.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class UserGatewayImplTest {

	private UserGatewayImpl userGateway;

	@BeforeEach
	void setUp() {
		userGateway = new UserGatewayImpl();
	}

	@Test
	void getUserByEmail_shouldReturnUserWithGivenValues() {
		String email = "user@example.com";
		String username = "username";
		Long id = 1L;

		Mono<User> result = userGateway.getUserByEmail(email, username, id);

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getEmail().equals(email) && user.getUsername().equals(username) && user.getId().equals(id))
				.verifyComplete();
	}

	@Test
	void getUserByEmail_shouldReturnEmptyWhenNoUser() {
		Mono<User> result = userGateway.getUserByEmail(null, null, null);

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getEmail() == null && user.getUsername() == null && user.getId() == null)
				.verifyComplete();
	}

	@Test
	void findByUsername_shouldReturnUserDetailsWhenUserExists() {
		String username = "username";
		User user = User.builder().username(username).build();

		UserGatewayImpl spyUserGateway = spy(userGateway);
		doReturn(Mono.just(user)).when(spyUserGateway).getUserByEmail(username, "", 0L);

		Mono<UserDetails> result = spyUserGateway.findByUsername(username);

		StepVerifier.create(result)
				.expectNextMatches(userDetails -> userDetails.getUsername().equals(username))
				.verifyComplete();
	}
}