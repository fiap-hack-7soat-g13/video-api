package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VideoStatusUpdateUseCase {

    private final VideoGateway videoGateway;

    public void execute(Long id, VideoStatus status) {
        Video video = videoGateway.findById(id).orElseThrow();
        video.setStatus(status);
        videoGateway.save(video);
    }

}
