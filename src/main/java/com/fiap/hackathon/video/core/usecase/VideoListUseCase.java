package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VideoListUseCase {

    private final VideoGateway videoGateway;

    public List<Video> execute(Long userId) {
        return videoGateway.findAll(userId);
    }

}
