package com.fiap.hackathon.video.app.adapter.input.queue.video.dto;

import com.fiap.hackathon.video.core.domain.VideoStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VideoStatusChangedEventTest {

	@Test
	void VideoStatusChangedEvent_shouldCreateEventWithGivenValues() {
		Long id = 1L;
		VideoStatus status = VideoStatus.SUCCEEDED;

		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(id)
				.status(status)
				.build();

		assertNotNull(event);
		assertEquals(id, event.getId());
		assertEquals(status, event.getStatus());
	}

	@Test
	void VideoStatusChangedEvent_shouldHandleNullValues() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(null)
				.status(null)
				.build();

		assertNotNull(event);
		assertEquals(null, event.getId());
		assertEquals(null, event.getStatus());
	}

}
