package com.fiap.hackathon.video.app.adapter.input.queue.video;

import com.fiap.hackathon.video.app.adapter.input.queue.video.dto.VideoStatusChangedEvent;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.usecase.VideoStatusUpdateUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class VideoStatusChangedConsumerTest {

	private VideoStatusUpdateUseCase videoStatusUpdateUseCase;
	private VideoStatusChangedConsumer videoStatusChangedConsumer;

	@BeforeEach
	void setUp() {
		videoStatusUpdateUseCase = mock(VideoStatusUpdateUseCase.class);
		videoStatusChangedConsumer = new VideoStatusChangedConsumer(videoStatusUpdateUseCase);
	}

	@Test
	void consume_shouldExecuteUseCaseWithValidEvent() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(1L)
				.status(VideoStatus.SUCCEEDED)
				.build();

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(1L, VideoStatus.SUCCEEDED);
	}

	@Test
	void consume_shouldHandleNullEvent() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(null)
				.status(null)
				.build();

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(null, null);
	}

	@Test
	void consume_shouldHandleNullId() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(null)
				.status(VideoStatus.SUCCEEDED)
				.build();

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(null, VideoStatus.SUCCEEDED);
	}

	@Test
	void consume_shouldHandleNullStatus() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(1L)
				.status(null)
				.build();

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(1L, null);
	}

}
