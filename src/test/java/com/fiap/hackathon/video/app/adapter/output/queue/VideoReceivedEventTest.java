package com.fiap.hackathon.video.app.adapter.output.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VideoReceivedEventTest {

	@Test
	void VideoReceivedEvent_shouldCreateEventWithGivenValues() {
		Long id = 1L;

		VideoReceivedEvent event = VideoReceivedEvent.builder().id(id).build();

		assertNotNull(event);
		assertEquals(id, event.getId());
	}

	@Test
	void VideoReceivedEvent_shouldHandleNullValues() {
		VideoReceivedEvent event = VideoReceivedEvent.builder().id(null).build();

		assertNotNull(event);
		assertEquals(null, event.getId());
	}
}