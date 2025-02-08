package com.fiap.hackathon.video.common.filter;

import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.domain.dto.ErrorDto;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CustomGlobalFilter implements WebFilter {

	private JwtTokenUtil jwtTokenUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (this.getToken(authHeader) != null) {

			var token = this.getToken(authHeader);

			String id;
			String username;
			String email;

			try {
				id = jwtTokenUtil.getIdFromToken(token);
				username = jwtTokenUtil.getUsernameFromToken(token);
				email = jwtTokenUtil.getEmailFromToken(token);
			} catch (Exception e) {
				return handleExpiredToken(exchange);
			}

			UserDetails userDetails = User.builder().id(StringUtils.isEmpty(id) ? null : Long.valueOf(id)).username(username).email(email).build();

			return chain.filter(exchange)
					.contextWrite(ReactiveSecurityContextHolder.withAuthentication(
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())))
					.onErrorResume(e -> handleError(exchange, e));

		}

		return chain.filter(exchange);
	}

	private Mono<Void> handleExpiredToken(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");

		ErrorDto errorDto = new ErrorDto(
				"Credenciais inválidas",
				"Token expirado ou inválido.",
				String.valueOf(HttpStatus.UNAUTHORIZED.value()));

		return exchange.getResponse()
				.writeWith(
						Mono.just(exchange.getResponse().bufferFactory().wrap(new Gson().toJson(errorDto).getBytes())));
	}

	private Mono<Void> handleError(ServerWebExchange exchange, Throwable e) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");

		ErrorDto errorDto = new ErrorDto(
				"Ops...",
				"Recurso não autorizado.",
				String.valueOf(HttpStatus.UNAUTHORIZED.value()));

		return exchange.getResponse()
				.writeWith(
						Mono.just(exchange.getResponse().bufferFactory().wrap(new Gson().toJson(errorDto).getBytes())));
	}

	private String getToken(String authorization) {

		if (authorization != null && authorization.startsWith("Bearer")) {
			return authorization.split(" ")[1];
		}

		return null;
	}

}