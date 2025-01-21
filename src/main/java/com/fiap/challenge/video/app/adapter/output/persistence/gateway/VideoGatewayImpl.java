package com.fiap.challenge.video.app.adapter.output.persistence.gateway;

import com.fiap.challenge.video.app.adapter.output.persistence.entity.VideoEntity;
import com.fiap.challenge.video.app.adapter.output.persistence.mapper.VideoMapper;
import com.fiap.challenge.video.app.adapter.output.persistence.repository.VideoRepository;
import com.fiap.challenge.video.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.video.core.domain.Video;
import com.fiap.challenge.video.core.gateways.VideoGateway;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class VideoGatewayImpl implements VideoGateway {

    private final VideoMapper mapper;
    private final VideoRepository repository;

    @Transactional
    public Video save(Video video) {
        VideoEntity videoEntity = mapper.toVideoEntity(video);
        VideoEntity videoSave = repository.save(videoEntity);
        return mapper.toVideo(videoSave);
    }

    public Video findById(Long id) {
        return repository.findById(id).map(mapper::toVideo).orElse(null);
    }

    public List<Video> findAll() {
        return mapper.toVideo(repository.findAll());
    }

    @Transactional
    public List<Video> findByName(String name) {
        return mapper.toVideo(repository.findByName(name));
    }

}
