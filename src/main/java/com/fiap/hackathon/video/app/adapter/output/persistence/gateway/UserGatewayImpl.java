package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.gateway.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UserGatewayImpl implements UserGateway, ReactiveUserDetailsService {

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

}
