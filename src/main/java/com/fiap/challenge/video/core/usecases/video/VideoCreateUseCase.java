package com.fiap.challenge.video.core.usecases.video;

import com.fiap.challenge.video.core.domain.Video;
import com.fiap.challenge.video.core.gateways.VideoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VideoCreateUseCase {

    private final VideoGateway videoGateway;

    public Video execute(Video video) {
        return videoGateway.save(video);
    }

}