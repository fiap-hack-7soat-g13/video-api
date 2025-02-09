package com.fiap.hackathon.video.core.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VideoTest {

	@Test
	void Video_shouldCreateVideoWithGivenValues() {
		Long id = 1L;
		String name = "Test Video";
		String contentType = "video/mp4";
		Long size = 1024L;
		VideoStatus status = VideoStatus.SUCCEEDED;
		LocalDateTime createdAt = LocalDateTime.now();
		Long createdBy = 1L;
		String createdByEmail = "user@example.com";

		Video video = new Video();
		video.setId(id);
		video.setName(name);
		video.setContentType(contentType);
		video.setSize(size);
		video.setStatus(status);
		video.setCreatedAt(createdAt);
		video.setCreatedBy(createdBy);
		video.setCreatedByEmail(createdByEmail);

		assertNotNull(video);
		assertEquals(id, video.getId());
		assertEquals(name, video.getName());
		assertEquals(contentType, video.getContentType());
		assertEquals(size, video.getSize());
		assertEquals(status, video.getStatus());
		assertEquals(createdAt, video.getCreatedAt());
		assertEquals(createdBy, video.getCreatedBy());
		assertEquals(createdByEmail, video.getCreatedByEmail());
	}

	@Test
	void Video_shouldHandleNullValues() {
		Video video = new Video();

		assertNotNull(video);
		assertNull(video.getId());
		assertNull(video.getName());
		assertNull(video.getContentType());
		assertNull(video.getSize());
		assertNull(video.getStatus());
		assertNull(video.getCreatedAt());
		assertNull(video.getCreatedBy());
		assertNull(video.getCreatedByEmail());
	}

	@Test
	void setName_shouldSetAndGetName() {
		Video video = new Video();
		String name = "New Video Name";

		video.setName(name);

		assertEquals(name, video.getName());
	}

	@Test
	void setContentType_shouldSetAndGetContentType() {
		Video video = new Video();
		String contentType = "video/mp4";

		video.setContentType(contentType);

		assertEquals(contentType, video.getContentType());
	}

	@Test
	void setSize_shouldSetAndGetSize() {
		Video video = new Video();
		Long size = 2048L;

		video.setSize(size);

		assertEquals(size, video.getSize());
	}

	@Test
	void setStatus_shouldSetAndGetStatus() {
		Video video = new Video();
		VideoStatus status = VideoStatus.PROCESSING;

		video.setStatus(status);

		assertEquals(status, video.getStatus());
	}

	@Test
	void setCreatedAt_shouldSetAndGetCreatedAt() {
		Video video = new Video();
		LocalDateTime createdAt = LocalDateTime.now();

		video.setCreatedAt(createdAt);

		assertEquals(createdAt, video.getCreatedAt());
	}

	@Test
	void setCreatedBy_shouldSetAndGetCreatedBy() {
		Video video = new Video();
		Long createdBy = 2L;

		video.setCreatedBy(createdBy);

		assertEquals(createdBy, video.getCreatedBy());
	}

	@Test
	void setCreatedByEmail_shouldSetAndGetCreatedByEmail() {
		Video video = new Video();
		String createdByEmail = "newuser@example.com";

		video.setCreatedByEmail(createdByEmail);

		assertEquals(createdByEmail, video.getCreatedByEmail());
	}
}