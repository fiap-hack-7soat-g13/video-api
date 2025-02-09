package com.fiap.hackathon.video.app.adapter.output.storage.fs;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileSystemLocationTest {

	@Test
	void FileSystemLocation_shouldCreateLocationWithGivenDirectory() {
		Path directory = Path.of("test-directory");

		FileSystemLocation location = new FileSystemLocation(directory);

		assertNotNull(location);
		assertEquals(directory, location.getDirectory());
	}

	@Test
	void FileSystemLocation_shouldHandleNullDirectory() {
		FileSystemLocation location = new FileSystemLocation(null);

		assertNotNull(location);
		assertEquals(null, location.getDirectory());
	}
}