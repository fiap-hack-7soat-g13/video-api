package com.fiap.hackathon.video.core.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

	@Test
	void User_shouldCreateUserWithGivenValues() {
		Long id = 1L;
		String username = "username";
		String email = "user@example.com";

		User user = User.builder().id(id).username(username).email(email).build();

		assertNotNull(user);
		assertEquals(id, user.getId());
		assertEquals(username, user.getUsername());
		assertEquals(email, user.getEmail());
	}

	@Test
	void User_shouldHandleNullValues() {
		User user = User.builder().id(null).username(null).email(null).build();

		assertNotNull(user);
		assertNull(user.getId());
		assertNull(user.getUsername());
		assertNull(user.getEmail());
	}

	@Test
	void getAuthorities_shouldReturnEmptyList() {
		User user = User.builder().build();

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		assertNotNull(authorities);
		assertTrue(authorities.isEmpty());
	}

	@Test
	void getPassword_shouldReturnEmptyString() {
		User user = User.builder().build();

		String password = user.getPassword();

		assertNotNull(password);
		assertEquals("", password);
	}

	@Test
	void isAccountNonExpired_shouldReturnFalse() {
		User user = User.builder().build();

		boolean result = user.isAccountNonExpired();

		assertFalse(result);
	}

	@Test
	void isAccountNonLocked_shouldReturnFalse() {
		User user = User.builder().build();

		boolean result = user.isAccountNonLocked();

		assertFalse(result);
	}

	@Test
	void isCredentialsNonExpired_shouldReturnFalse() {
		User user = User.builder().build();

		boolean result = user.isCredentialsNonExpired();

		assertFalse(result);
	}

	@Test
	void isEnabled_shouldReturnFalse() {
		User user = User.builder().build();

		boolean result = user.isEnabled();

		assertFalse(result);
	}
}