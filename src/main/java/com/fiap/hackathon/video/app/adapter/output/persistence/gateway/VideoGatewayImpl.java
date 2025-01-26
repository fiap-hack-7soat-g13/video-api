package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.app.adapter.output.persistence.entity.VideoEntity;
import com.fiap.hackathon.video.app.adapter.output.persistence.mapper.VideoEntityMapper;
import com.fiap.hackathon.video.app.adapter.output.persistence.repository.VideoRepository;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class VideoGatewayImpl implements VideoGateway {

    private final VideoRepository videoRepository;
    private final VideoEntityMapper videoEntityMapper;

    @Override
    @Transactional
    public Video save(Video video) {
        VideoEntity videoEntity = videoEntityMapper.toVideoEntity(video);
        VideoEntity savedVideoEntity = videoRepository.save(videoEntity);
        return videoEntityMapper.toVideo(savedVideoEntity);
    }

    @Override
    @Transactional
    public Optional<Video> findById(Long id) {
        Optional<VideoEntity> videoEntity = videoRepository.findById(id);
        return videoEntity.map(videoEntityMapper::toVideo);
    }

    @Override
    @Transactional
    public List<Video> findAll() {
        List<VideoEntity> videos = videoRepository.findAll();
        return videos.stream().map(videoEntityMapper::toVideo).toList();
    }

}
