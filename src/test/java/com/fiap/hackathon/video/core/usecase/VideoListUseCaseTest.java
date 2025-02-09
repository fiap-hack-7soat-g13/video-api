package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VideoListUseCaseTest {

	private final VideoGateway videoGateway = mock(VideoGateway.class);
	private final VideoListUseCase useCase = new VideoListUseCase(videoGateway);

	@Test
	void execute_shouldReturnListOfVideosWhenUserIdExists() {
		Long userId = 1L;
		Video video = new Video();
		video.setId(1L);
		video.setName("Test Video");

		when(videoGateway.findAll(userId)).thenReturn(List.of(video));

		List<Video> result = useCase.execute(userId);

		assertEquals(1, result.size());
		assertEquals(video, result.get(0));
	}

	@Test
	void execute_shouldReturnEmptyListWhenUserIdDoesNotExist() {
		Long userId = 1L;

		when(videoGateway.findAll(userId)).thenReturn(Collections.emptyList());

		List<Video> result = useCase.execute(userId);

		assertEquals(0, result.size());
	}

	@Test
	void execute_shouldHandleNullUserId() {
		when(videoGateway.findAll(null)).thenReturn(Collections.emptyList());

		List<Video> result = useCase.execute(null);

		assertEquals(0, result.size());
	}
}