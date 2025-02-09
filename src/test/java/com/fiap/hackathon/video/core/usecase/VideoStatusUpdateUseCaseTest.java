package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VideoStatusUpdateUseCaseTest {

	private final VideoGateway videoGateway = mock(VideoGateway.class);
	private final VideoStatusUpdateUseCase useCase = new VideoStatusUpdateUseCase(videoGateway);

	@Test
	void execute_shouldUpdateVideoStatusWhenVideoExists() {
		Long videoId = 1L;
		VideoStatus newStatus = VideoStatus.PROCESSING;
		Video video = new Video();
		video.setId(videoId);
		video.setStatus(VideoStatus.SUCCEEDED);

		when(videoGateway.findById(videoId)).thenReturn(Optional.of(video));

		useCase.execute(videoId, newStatus);

		verify(videoGateway).save(video);
		assertEquals(newStatus, video.getStatus());
	}

	@Test
	void execute_shouldThrowExceptionWhenVideoDoesNotExist() {
		Long videoId = 1L;
		VideoStatus newStatus = VideoStatus.PROCESSING;

		when(videoGateway.findById(videoId)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> useCase.execute(videoId, newStatus));
	}

	@Test
	void execute_shouldNotUpdateStatusWhenStatusIsNull() {
		Long videoId = 1L;
		Video video = new Video();
		video.setId(videoId);
		video.setStatus(VideoStatus.RECEIVED);

		when(videoGateway.findById(videoId)).thenReturn(Optional.of(video));
		video.setStatus(VideoStatus.SUCCEEDED);
		when(videoGateway.save(video)).thenReturn(video);

		useCase.execute(videoId, null);

		verify(videoGateway).save(any(Video.class));
	}
}