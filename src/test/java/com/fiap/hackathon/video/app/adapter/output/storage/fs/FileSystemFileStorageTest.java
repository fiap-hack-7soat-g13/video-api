package com.fiap.hackathon.video.app.adapter.output.storage.fs;

import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileSystemFileStorageTest {

	private FileSystemFileStorage fileSystemFileStorage;
	private final Path uploadDirectory = Path.of("upload-directory");
	private final Path videoDirectory = Path.of("video-directory");
	private final Path thumbnailDirectory = Path.of("thumbnail-directory");

	@BeforeEach
	void setUp() throws IOException {
		fileSystemFileStorage = new FileSystemFileStorage(uploadDirectory.toString(), videoDirectory.toString(), thumbnailDirectory.toString());
		Files.createDirectories(videoDirectory);
		Files.createDirectories(thumbnailDirectory);
	}

	@Test
	void create_shouldUploadFileToFileSystem() throws IOException {
		String name = "test-video.mp4";
		MultipartFile source = mock(MultipartFile.class);
		File tempFile = File.createTempFile("test", "tmp");

		when(source.getInputStream()).thenReturn(new FileInputStream(tempFile));

		fileSystemFileStorage.create(Location.VIDEO, name, source);

		Path targetPath = videoDirectory.resolve(name);
		assertTrue(Files.exists(targetPath));
	}

	@Test
	void create_shouldThrowRuntimeExceptionOnIOException() throws IOException {
		String name = "test-video.mp4";
		MultipartFile source = mock(MultipartFile.class);

		when(source.getInputStream()).thenThrow(new IOException());

		assertThrows(RuntimeException.class, () -> fileSystemFileStorage.create(Location.VIDEO, name, source));
	}

	@Test
	void download_shouldReturnInputStreamSource() {
		String name = "test-video.mp4";
		Path sourcePath = videoDirectory.resolve(name);

		InputStreamSource result = fileSystemFileStorage.download(Location.VIDEO, name);

		assertNotNull(result);
		assertEquals(sourcePath, ((FileSystemResource) result).getFile().toPath());
	}

}
