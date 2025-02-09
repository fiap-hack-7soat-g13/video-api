package com.fiap.hackathon.video.app.adapter.input.web.video.dto;

import com.fiap.hackathon.video.core.domain.VideoStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VideoResponseTest {

	@Test
	void VideoResponse_shouldCreateResponseWithGivenValues() {
		Long id = 1L;
		String name = "Sample Video";
		String contentType = "video/mp4";
		Long size = 1024L;
		VideoStatus status = VideoStatus.SUCCEEDED;
		LocalDateTime createdAt = LocalDateTime.now();

		VideoResponse response = new VideoResponse();
		response.setId(id);
		response.setName(name);
		response.setContentType(contentType);
		response.setSize(size);
		response.setStatus(status);
		response.setCreatedAt(createdAt);

		assertNotNull(response);
		assertEquals(id, response.getId());
		assertEquals(name, response.getName());
		assertEquals(contentType, response.getContentType());
		assertEquals(size, response.getSize());
		assertEquals(status, response.getStatus());
		assertEquals(createdAt, response.getCreatedAt());
	}

	@Test
	void VideoResponse_shouldHandleNullValues() {
		VideoResponse response = new VideoResponse();
		response.setId(null);
		response.setName(null);
		response.setContentType(null);
		response.setSize(null);
		response.setStatus(null);
		response.setCreatedAt(null);

		assertNotNull(response);
		assertEquals(null, response.getId());
		assertEquals(null, response.getName());
		assertEquals(null, response.getContentType());
		assertEquals(null, response.getSize());
		assertEquals(null, response.getStatus());
		assertEquals(null, response.getCreatedAt());
	}

}
