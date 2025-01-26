package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.common.exception.NotFoundException;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VideoGetUseCase {

    private final VideoGateway videoGateway;

    public Video execute(Long id) {
        return videoGateway.findById(id).orElseThrow(NotFoundException::new);
    }

}
