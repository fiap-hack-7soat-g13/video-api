package com.fiap.hackathon.video.core.gateway;

import com.fiap.hackathon.video.core.domain.User;
import jakarta.mail.MessagingException;
import reactor.core.publisher.Mono;

public interface UserGateway {

	Mono<User> getUserByEmail(String email, String username, Long id);

	void sendMmail(String email, Long videoId) throws MessagingException;

}
