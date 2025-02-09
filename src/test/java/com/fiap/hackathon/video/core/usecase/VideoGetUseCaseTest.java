package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.common.exception.NotFoundException;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VideoGetUseCaseTest {

	private final VideoGateway videoGateway = mock(VideoGateway.class);
	private final VideoGetUseCase useCase = new VideoGetUseCase(videoGateway);

	@Test
	void execute_shouldReturnVideoWhenIdExists() {
		Long videoId = 1L;
		Video video = new Video();
		video.setId(videoId);
		video.setName("Test Video");

		when(videoGateway.findById(videoId)).thenReturn(Optional.of(video));

		Video result = useCase.execute(videoId);

		assertEquals(video, result);
	}

	@Test
	void execute_shouldThrowNotFoundExceptionWhenIdDoesNotExist() {
		Long videoId = 1L;

		when(videoGateway.findById(videoId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> useCase.execute(videoId));
	}

}