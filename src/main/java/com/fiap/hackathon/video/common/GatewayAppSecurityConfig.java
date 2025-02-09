package com.fiap.hackathon.video.common;

import com.fiap.hackathon.video.common.filter.CustomGlobalFilter;
import com.fiap.hackathon.video.common.filter.JwtTokenUtil;
import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.usecase.GetUserByEmailUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GatewayAppSecurityConfig {

	private JwtTokenUtil jwtTokenUtil;
	private GetUserByEmailUseCase getUserByEmailUseCase;
//	private RequestContextFilter requestContextFilter;

	public GatewayAppSecurityConfig(JwtTokenUtil jwtTokenUtil, GetUserByEmailUseCase getUserByEmailUseCase
//	                                RequestContextFilter requestContextFilter
	) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.getUserByEmailUseCase = getUserByEmailUseCase;
//		this.requestContextFilter = requestContextFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		UserDetails userDetails = User.builder().username("teste").id(1L).email("teste@teste.com.br").build();
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(req -> {
					req.anyRequest().permitAll();
				})
//				.addFilterBefore(new CustomGlobalFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
//				.addFilterBefore(requestContextFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

//	@Bean
//	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
//		return http
//				.httpBasic(Customizer.withDefaults())
//				.csrf(csrf -> csrf.disable())
//				.addFilterBefore(new CustomGlobalFilter(jwtTokenUtil),
//						SecurityWebFiltersOrder.AUTHENTICATION)
//				.build();
//	}

//	@Bean
//	ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService reactiveUserDetailsService,
//	                                                    PasswordEncoder passwordEncoder) {
//		UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
//				reactiveUserDetailsService);
//		authenticationManager.setPasswordEncoder(passwordEncoder);
//		return authenticationManager;
//	}
//
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
}