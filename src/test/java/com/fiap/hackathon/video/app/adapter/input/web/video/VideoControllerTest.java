package com.fiap.hackathon.video.app.adapter.input.web.video;

import com.fiap.hackathon.video.app.adapter.input.web.video.dto.VideoResponse;
import com.fiap.hackathon.video.app.adapter.input.web.video.mapper.VideoResponseMapper;
import com.fiap.hackathon.video.core.common.exception.NotFoundException;
import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoControllerTest {

	private VideoGenerateUploadUrlUseCase videoGenerateUploadLinkUseCase;
	private VideoCreateUseCase videoCreateUseCase;
	private VideoGetUseCase videoGetUseCase;
	private VideoListUseCase videoListUseCase;
	private ThumbnailDownloadUseCase thumbnailDownloadUseCase;
	private VideoResponseMapper videoResponseMapper;
	private VideoController videoController;
	private Authentication authentication;
	private SecurityContext securityContext;
	private SendMailUseCase sendMailUseCase;

	@BeforeEach
	void setUp() {
		videoGenerateUploadLinkUseCase = mock(VideoGenerateUploadUrlUseCase.class);
		videoCreateUseCase = mock(VideoCreateUseCase.class);
		videoGetUseCase = mock(VideoGetUseCase.class);
		videoListUseCase = mock(VideoListUseCase.class);
		thumbnailDownloadUseCase = mock(ThumbnailDownloadUseCase.class);
		videoResponseMapper = mock(VideoResponseMapper.class);
		sendMailUseCase = mock(SendMailUseCase.class);
		videoController = new VideoController(videoGenerateUploadLinkUseCase, videoCreateUseCase, videoGetUseCase,
				videoListUseCase, thumbnailDownloadUseCase, videoResponseMapper, sendMailUseCase);
		authentication = mock(Authentication.class);
		securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	void get_shouldReturnVideoResponseWhenAuthenticatedAndVideoExists() {
		Long videoId = 1L;
		User user = User.builder().id(1L).build();
		Video video = new Video();
		video.setCreatedBy(1L);
		VideoResponse videoResponse = new VideoResponse();

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(authentication.getPrincipal()).thenReturn(user);
		when(videoGetUseCase.execute(videoId)).thenReturn(video);
		when(videoResponseMapper.toVideoResponse(video)).thenReturn(videoResponse);

		VideoResponse response = videoController.get(videoId);

		assertNotNull(response);
		assertEquals(videoResponse, response);
	}

	@Test
	void get_shouldThrowNotFoundExceptionWhenVideoDoesNotBelongToUser() {
		Long videoId = 1L;
		User user = User.builder().id(1L).build();
		Video video = new Video();
		video.setCreatedBy(2L);

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(authentication.getPrincipal()).thenReturn(user);
		when(videoGetUseCase.execute(videoId)).thenReturn(video);

		assertThrows(NotFoundException.class, () -> videoController.get(videoId));
	}

	@Test
	void list_shouldReturnListOfVideoResponsesWhenAuthenticated() {
		User user = User.builder().id(1L).build();
		List<Video> videos = List.of(new Video());
		List<VideoResponse> videoResponses = List.of(new VideoResponse());

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(authentication.getPrincipal()).thenReturn(user);
		when(videoListUseCase.execute(user.getId())).thenReturn(videos);
		when(videoResponseMapper.toVideoResponse(any(Video.class))).thenReturn(videoResponses.get(0));

		List<VideoResponse> response = videoController.list();

		assertNotNull(response);
		assertEquals(videoResponses.size(), response.size());
	}

	@Test
	void thumbnailDownload_shouldReturnResponseEntityWhenAuthenticatedAndVideoExists() {
		Long videoId = 1L;
		User user = User.builder().id(1L).build();
		Video video = new Video();
		video.setCreatedBy(1L);
		String link = "link";

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(authentication.getPrincipal()).thenReturn(user);
		when(videoGetUseCase.execute(videoId)).thenReturn(video);
		when(thumbnailDownloadUseCase.execute(videoId)).thenReturn(link);

		ResponseEntity<Void> response = videoController.thumbnailDownload(videoId);

		assertNotNull(response);
		assertEquals(HttpStatus.SEE_OTHER, response.getStatusCode());
		assertEquals(URI.create(link), response.getHeaders().getLocation());
	}

	@Test
	void thumbnailDownload_shouldThrowNotFoundExceptionWhenVideoDoesNotBelongToUser() {
		Long videoId = 1L;
		User user = User.builder().id(1L).build();
		Video video = new Video();
		video.setCreatedBy(2L);

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(authentication.getPrincipal()).thenReturn(user);
		when(videoGetUseCase.execute(videoId)).thenReturn(video);

		assertThrows(NotFoundException.class, () -> videoController.thumbnailDownload(videoId));
	}

}
