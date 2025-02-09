package com.fiap.hackathon.video.app.adapter.output.queue.rabbitmq;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoReceivedEvent;
import com.fiap.hackathon.video.core.domain.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class VideoReceivedDispatcherImplTest {

	private RabbitTemplate rabbitTemplate;
	private VideoReceivedDispatcherImpl videoReceivedDispatcher;
	private final String videoReceivedQueue = "video.received.queue";

	@BeforeEach
	void setUp() {
		rabbitTemplate = mock(RabbitTemplate.class);
		videoReceivedDispatcher = new VideoReceivedDispatcherImpl(rabbitTemplate, videoReceivedQueue);
	}

	@Test
	void dispatch_shouldSendVideoReceivedEventToQueue() {
		Video video = new Video();
		video.setId(1L);

		videoReceivedDispatcher.dispatch(video);

		ArgumentCaptor<VideoReceivedEvent> eventCaptor = ArgumentCaptor.forClass(VideoReceivedEvent.class);
		verify(rabbitTemplate).convertAndSend(eq(videoReceivedQueue), eventCaptor.capture());

		VideoReceivedEvent capturedEvent = eventCaptor.getValue();
		assertNotNull(capturedEvent);
		assertEquals(video.getId(), capturedEvent.getId());
	}

}
