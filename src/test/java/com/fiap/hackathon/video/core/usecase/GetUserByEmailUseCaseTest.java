package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetUserByEmailUseCaseTest {

	private final UserGateway userGateway = mock(UserGateway.class);
	private final GetUserByEmailUseCase useCase = new GetUserByEmailUseCase(userGateway);

	@Test
	void execute_shouldReturnUserWhenEmailUsernameAndIdAreValid() {
		String email = "test@example.com";
		String username = "testuser";
		Long id = 1L;
		User user = User.builder().id(id).username(username).email(email).build();

		when(userGateway.getUserByEmail(email, username, id)).thenReturn(Mono.just(user));

		User result = useCase.execute(email, username, id);

		assertEquals(user, result);
	}

	@Test
	void execute_shouldHandleNullEmail() {
		String email = null;
		String username = "testuser";
		Long id = 1L;

		when(userGateway.getUserByEmail(email, username, id)).thenReturn(Mono.empty());

		User result = useCase.execute(email, username, id);

		assertEquals(null, result);
	}

	@Test
	void execute_shouldHandleNullUsername() {
		String email = "test@example.com";
		String username = null;
		Long id = 1L;

		when(userGateway.getUserByEmail(email, username, id)).thenReturn(Mono.empty());

		User result = useCase.execute(email, username, id);

		assertEquals(null, result);
	}

	@Test
	void execute_shouldHandleNullId() {
		String email = "test@example.com";
		String username = "testuser";
		Long id = null;

		when(userGateway.getUserByEmail(email, username, id)).thenReturn(Mono.empty());

		User result = useCase.execute(email, username, id);

		assertEquals(null, result);
	}

	@Test
	void execute_shouldHandleNonExistentUser() {
		String email = "nonexistent@example.com";
		String username = "nonexistentuser";
		Long id = 999L;

		when(userGateway.getUserByEmail(email, username, id)).thenReturn(Mono.empty());

		User result = useCase.execute(email, username, id);

		assertEquals(null, result);
	}
}