package com.fiap.hackathon.video.common.filter;

import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.domain.dto.ErrorDto;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;

@AllArgsConstructor
public class CustomGlobalFilter implements WebFilter, Filter {

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

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

		String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith(HttpHeaders.AUTHORIZATION)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		var token = this.getToken(authHeader);

		String id = jwtTokenUtil.getIdFromToken(token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		String email = jwtTokenUtil.getEmailFromToken(token);

		UserDetails userDetails = User.builder().id(StringUtils.isEmpty(id) ? null : Long.valueOf(id)).username(username).email(email).build();
		if (userDetails != null) {
			UsernamePasswordAuthenticationToken authorization = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authorization);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
}