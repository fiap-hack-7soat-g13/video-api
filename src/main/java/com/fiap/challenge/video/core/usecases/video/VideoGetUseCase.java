package com.fiap.challenge.video.core.usecases.video;

import com.fiap.challenge.video.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.video.core.domain.Video;
import com.fiap.challenge.video.core.gateways.VideoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VideoGetUseCase {

    private final VideoGateway videoGateway;

    public Video execute(Long id) {
        Video video = videoGateway.findById(id);
        if (video == null)
            throw new EntityNotFoundException();

        return video;
    }

}