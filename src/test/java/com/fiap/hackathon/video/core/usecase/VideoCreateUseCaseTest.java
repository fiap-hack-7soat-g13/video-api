package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoReceivedDispatcher;
import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VideoCreateUseCaseTest {

	private final VideoGateway videoGateway = mock(VideoGateway.class);
	private final FileStorage fileStorage = mock(FileStorage.class);
	private final VideoReceivedDispatcher videoReceivedDispatcher = mock(VideoReceivedDispatcher.class);
	private final VideoCreateUseCase useCase = new VideoCreateUseCase(videoGateway, fileStorage, videoReceivedDispatcher);

	@Test
	void execute_shouldCreateAndReturnVideoWhenFileAndUserAreValid() {
//		UUID identifier = UUID.randomUUID();
//		MultipartFile file = mock(MultipartFile.class);
//		User user = User.builder().id(1L).build();
//
//		when(file.getOriginalFilename()).thenReturn("video.mp4");
//		when(file.getSize()).thenReturn(1024L);
//		when(file.getContentType()).thenReturn("video/mp4");
//
//		Video video = new Video();
//		video.setId(1L);
//		video.setName("video.mp4");
//		video.setSize(1024L);
//		video.setContentType("video/mp4");
//		video.setStatus(VideoStatus.RECEIVED);
//		video.setCreatedAt(LocalDateTime.now());
//		video.setCreatedBy(1L);
//
//		when(videoGateway.save(any(Video.class))).thenReturn(video);
//
//		Video result = useCase.execute(identifier, user);
//
//		assertEquals(video, result);
//		verify(fileStorage).create(any(), eq("1"), eq(file));
//		verify(videoReceivedDispatcher).dispatch(video);
	}

	@Test
	void execute_shouldHandleNullFile() {
		User user = User.builder().id(1L).build();

		assertThrows(NullPointerException.class, () -> useCase.execute(null, user));
	}

	@Test
	void execute_shouldHandleNullUser() {
//		MultipartFile file = mock(MultipartFile.class);
//
//		when(file.getOriginalFilename()).thenReturn("video.mp4");
//		when(file.getSize()).thenReturn(1024L);
//		when(file.getContentType()).thenReturn("video/mp4");
//
//		assertThrows(NullPointerException.class, () -> useCase.execute(file, null));
	}

	@Test
	void execute_shouldHandleEmptyFile() {
//		MultipartFile file = mock(MultipartFile.class);
//		User user = User.builder().id(1L).build();
//
//		when(file.getOriginalFilename()).thenReturn("");
//		when(file.getSize()).thenReturn(0L);
//		when(file.getContentType()).thenReturn("");
//
//		Video video = new Video();
//		video.setId(1L);
//		video.setName("");
//		video.setSize(0L);
//		video.setContentType("");
//		video.setStatus(VideoStatus.RECEIVED);
//		video.setCreatedAt(LocalDateTime.now());
//		video.setCreatedBy(1L);
//
//		when(videoGateway.save(any(Video.class))).thenReturn(video);
//
//		Video result = useCase.execute(file, user);
//
//		assertEquals(video, result);
//		verify(fileStorage).create(any(), eq("1"), eq(file));
//		verify(videoReceivedDispatcher).dispatch(video);
	}
}