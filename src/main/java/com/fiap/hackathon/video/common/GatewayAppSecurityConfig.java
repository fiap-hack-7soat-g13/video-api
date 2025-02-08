package com.fiap.hackathon.video.common;

import com.fiap.hackathon.video.common.filter.CustomGlobalFilter;
import com.fiap.hackathon.video.common.filter.JwtTokenUtil;
import com.fiap.hackathon.video.core.usecase.GetUserByEmailUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GatewayAppSecurityConfig {

	private JwtTokenUtil jwtTokenUtil;
	private GetUserByEmailUseCase getUserByEmailUseCase;

	public GatewayAppSecurityConfig(JwtTokenUtil jwtTokenUtil, GetUserByEmailUseCase getUserByEmailUseCase) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.getUserByEmailUseCase = getUserByEmailUseCase;
	}

	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		return http
				.httpBasic(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.addFilterBefore(new CustomGlobalFilter(jwtTokenUtil),
						SecurityWebFiltersOrder.AUTHENTICATION)
				.build();
	}

	@Bean
	ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService reactiveUserDetailsService,
	                                                    PasswordEncoder passwordEncoder) {
		UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
				reactiveUserDetailsService);
		authenticationManager.setPasswordEncoder(passwordEncoder);
		return authenticationManager;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}