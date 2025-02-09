package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.app.adapter.output.persistence.entity.VideoEntity;
import com.fiap.hackathon.video.app.adapter.output.persistence.mapper.VideoEntityMapper;
import com.fiap.hackathon.video.app.adapter.output.persistence.repository.VideoRepository;
import com.fiap.hackathon.video.core.domain.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VideoGatewayImplTest {

	private VideoRepository videoRepository;
	private VideoEntityMapper videoEntityMapper;
	private VideoGatewayImpl videoGateway;

	@BeforeEach
	void setUp() {
		videoRepository = mock(VideoRepository.class);
		videoEntityMapper = mock(VideoEntityMapper.class);
		videoGateway = new VideoGatewayImpl(videoRepository, videoEntityMapper);
	}

	@Test
	void save_shouldReturnSavedVideo() {
		Video video = new Video();
		VideoEntity videoEntity = new VideoEntity();
		VideoEntity savedVideoEntity = new VideoEntity();

		when(videoEntityMapper.toVideoEntity(video)).thenReturn(videoEntity);
		when(videoRepository.save(videoEntity)).thenReturn(savedVideoEntity);
		when(videoEntityMapper.toVideo(savedVideoEntity)).thenReturn(video);

		Video result = videoGateway.save(video);

		assertNotNull(result);
		assertEquals(video, result);
	}

	@Test
	void findById_shouldReturnVideoWhenExists() {
		Long id = 1L;
		VideoEntity videoEntity = new VideoEntity();
		Video video = new Video();

		when(videoRepository.findById(id)).thenReturn(Optional.of(videoEntity));
		when(videoEntityMapper.toVideo(videoEntity)).thenReturn(video);

		Optional<Video> result = videoGateway.findById(id);

		assertTrue(result.isPresent());
		assertEquals(video, result.get());
	}

	@Test
	void findById_shouldReturnEmptyWhenNotExists() {
		Long id = 1L;

		when(videoRepository.findById(id)).thenReturn(Optional.empty());

		Optional<Video> result = videoGateway.findById(id);

		assertFalse(result.isPresent());
	}

	@Test
	void findAll_shouldReturnListOfVideos() {
		Long userId = 1L;
		List<VideoEntity> videoEntities = List.of(new VideoEntity());
		List<Video> videos = List.of(new Video());

		when(videoRepository.findAllByCreatedBy(userId)).thenReturn(videoEntities);
		when(videoEntityMapper.toVideo(videoEntities.get(0))).thenReturn(videos.get(0));

		List<Video> result = videoGateway.findAll(userId);

		assertNotNull(result);
		assertEquals(videos.size(), result.size());
	}
}