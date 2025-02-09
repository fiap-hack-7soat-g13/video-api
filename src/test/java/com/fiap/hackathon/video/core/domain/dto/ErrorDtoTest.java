package com.fiap.hackathon.video.core.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorDtoTest {

	@Test
	void ErrorDto_shouldCreateErrorDtoWithGivenValues() {
		String title = "Error Title";
		String message = "Error Message";
		String code = "Error Code";

		ErrorDto errorDto = new ErrorDto(title, message, code);

		assertNotNull(errorDto);
		assertEquals(title, errorDto.getTitle());
		assertEquals(message, errorDto.getMessage());
		assertEquals(code, errorDto.getCode());
	}

	@Test
	void ErrorDto_shouldHandleNullValues() {
		ErrorDto errorDto = new ErrorDto(null, null, null);

		assertNotNull(errorDto);
		assertEquals(null, errorDto.getTitle());
		assertEquals(null, errorDto.getMessage());
		assertEquals(null, errorDto.getCode());
	}

	@Test
	void ErrorDto_shouldSetAndGetTitle() {
		ErrorDto errorDto = new ErrorDto(null, null, null);
		String title = "New Title";

		errorDto.setTitle(title);

		assertEquals(title, errorDto.getTitle());
	}

	@Test
	void ErrorDto_shouldSetAndGetMessage() {
		ErrorDto errorDto = new ErrorDto(null, null, null);
		String message = "New Message";

		errorDto.setMessage(message);

		assertEquals(message, errorDto.getMessage());
	}

	@Test
	void ErrorDto_shouldSetAndGetCode() {
		ErrorDto errorDto = new ErrorDto(null, null, null);
		String code = "New Code";

		errorDto.setCode(code);

		assertEquals(code, errorDto.getCode());
	}
}
