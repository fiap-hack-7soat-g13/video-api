package com.fiap.hackathon.video.app.adapter.output.persistence.entity;

import com.fiap.hackathon.video.core.domain.VideoStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VideoEntityTest {

	@Test
	void VideoEntity_shouldCreateEntityWithGivenValues() {
		Long id = 1L;
		String name = "Sample Video";
		String contentType = "video/mp4";
		Long size = 1024L;
		VideoStatus status = VideoStatus.SUCCEEDED;
		LocalDateTime createdAt = LocalDateTime.now();
		Long createdBy = 1L;
		String createdByEmail = "user@example.com";

		VideoEntity entity = new VideoEntity();
		entity.setId(id);
		entity.setName(name);
		entity.setContentType(contentType);
		entity.setSize(size);
		entity.setStatus(status);
		entity.setCreatedAt(createdAt);
		entity.setCreatedBy(createdBy);
		entity.setCreatedByEmail(createdByEmail);

		assertNotNull(entity);
		assertEquals(id, entity.getId());
		assertEquals(name, entity.getName());
		assertEquals(contentType, entity.getContentType());
		assertEquals(size, entity.getSize());
		assertEquals(status, entity.getStatus());
		assertEquals(createdAt, entity.getCreatedAt());
		assertEquals(createdBy, entity.getCreatedBy());
		assertEquals(createdByEmail, entity.getCreatedByEmail());
	}

	@Test
	void VideoEntity_shouldHandleNullValues() {
		VideoEntity entity = new VideoEntity();
		entity.setId(null);
		entity.setName(null);
		entity.setContentType(null);
		entity.setSize(null);
		entity.setStatus(null);
		entity.setCreatedAt(null);
		entity.setCreatedBy(null);
		entity.setCreatedByEmail(null);

		assertNotNull(entity);
		assertEquals(null, entity.getId());
		assertEquals(null, entity.getName());
		assertEquals(null, entity.getContentType());
		assertEquals(null, entity.getSize());
		assertEquals(null, entity.getStatus());
		assertEquals(null, entity.getCreatedAt());
		assertEquals(null, entity.getCreatedBy());
		assertEquals(null, entity.getCreatedByEmail());
	}
}
