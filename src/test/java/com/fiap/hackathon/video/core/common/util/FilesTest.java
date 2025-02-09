package com.fiap.hackathon.video.core.common.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilesTest {

	@Test
	void createTempFileAndExecute_shouldExecuteConsumerWithTempFile() {
		Consumer<Path> consumer = mock(Consumer.class);

		Files.createTempFileAndExecute(consumer);

		verify(consumer).accept(any(Path.class));
	}

	@Test
	void createTempFileAndExecute_shouldDeleteTempFileAfterExecution() {
		Consumer<Path> consumer = mock(Consumer.class);
		Path tempFile = Files.createTempFile();

		Files.createTempFileAndExecute(consumer);

		assertTrue(java.nio.file.Files.exists(tempFile));
	}

	@Test
	void createTempFile_shouldCreateTempFile() {
		Path tempFile = Files.createTempFile();

		assertNotNull(tempFile);
		assertTrue(java.nio.file.Files.exists(tempFile));
	}

	@Test
	void createTempFile_shouldThrowRuntimeExceptionOnIOException() {
		try (var mockedStatic = mockStatic(java.nio.file.Files.class)) {
			mockedStatic.when(() -> java.nio.file.Files.createTempFile(null, null)).thenThrow(new IOException());

			assertThrows(RuntimeException.class, Files::createTempFile);
		}
	}

	@Test
	void deleteQuietly_shouldDeleteFile() {
		Path tempFile = Files.createTempFile();

		Files.deleteQuietly(tempFile);

		assertFalse(java.nio.file.Files.exists(tempFile));
	}

	@Test
	void deleteQuietly_shouldHandleNullPath() {
		assertDoesNotThrow(() -> Files.deleteQuietly(null));
	}

	@Test
	void deleteQuietly_shouldLogErrorOnIOException() {
		Path tempFile = Files.createTempFile();
		try (var mockedStatic = mockStatic(java.nio.file.Files.class)) {
			mockedStatic.when(() -> java.nio.file.Files.delete(tempFile)).thenThrow(new IOException());

			Files.deleteQuietly(tempFile);
		}
	}
}
