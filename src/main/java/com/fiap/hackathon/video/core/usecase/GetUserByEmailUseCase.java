package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.gateway.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserByEmailUseCase {

	private final UserGateway userGateway;

	public User execute(String email, String username, Long id) {
		return userGateway.getUserByEmail(email, username, id).block();
	}

}
