package com.fiap.hackathon.video.app.adapter.input.queue.video;

import com.fiap.hackathon.video.app.adapter.input.queue.video.dto.VideoStatusChangedEvent;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.usecase.SendMailUseCase;
import com.fiap.hackathon.video.core.usecase.VideoGetUseCase;
import com.fiap.hackathon.video.core.usecase.VideoStatusUpdateUseCase;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class VideoStatusChangedConsumerTest {

	private VideoStatusUpdateUseCase videoStatusUpdateUseCase;
	private VideoGetUseCase videoGetUseCase;
	private SendMailUseCase sendMailUseCase;
	private VideoStatusChangedConsumer videoStatusChangedConsumer;

	@BeforeEach
	void setUp() {
		videoStatusUpdateUseCase = mock(VideoStatusUpdateUseCase.class);
		videoGetUseCase = mock(VideoGetUseCase.class);
		sendMailUseCase = mock(SendMailUseCase.class);
		videoStatusChangedConsumer = new VideoStatusChangedConsumer(videoStatusUpdateUseCase, videoGetUseCase, sendMailUseCase);
	}

	@Test
	void consume_shouldExecuteUseCaseWithValidEvent() throws MessagingException {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(1L)
				.status(VideoStatus.SUCCEEDED)
				.build();

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(1L, VideoStatus.SUCCEEDED);
	}

	@Test
	void consume_shouldHandleNullEvent() throws MessagingException {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(null)
				.status(null)
				.build();

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(null, null);
	}

	@Test
	void consume_shouldHandleNullId() throws MessagingException {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(null)
				.status(VideoStatus.SUCCEEDED)
				.build();

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(null, VideoStatus.SUCCEEDED);
	}

	@Test
	void consume_shouldHandleNullStatus() throws MessagingException {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(1L)
				.status(null)
				.build();

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(1L, null);
	}

	@Test
	void consume_shouldSendMailWhenStatusIsFailed() throws MessagingException {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(1L)
				.status(VideoStatus.FAILED)
				.build();
		Video video = new Video();
		video.setCreatedByEmail("test@example.com");

		when(videoGetUseCase.execute(1L)).thenReturn(video);

		videoStatusChangedConsumer.consume(event);

		verify(videoStatusUpdateUseCase, times(1)).execute(1L, VideoStatus.FAILED);
		verify(videoGetUseCase, times(1)).execute(1L);
		verify(sendMailUseCase, times(1)).execute("test@example.com", 1L);
	}
}