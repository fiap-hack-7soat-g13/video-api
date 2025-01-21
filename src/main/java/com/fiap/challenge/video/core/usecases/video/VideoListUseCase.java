package com.fiap.challenge.video.core.usecases.video;

import com.fiap.challenge.video.core.domain.Video;
import com.fiap.challenge.video.core.gateways.VideoGateway;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VideoListUseCase {

    private final VideoGateway videoGateway;

    public List<Video> execute(String name) {
        return StringUtils.isBlank(name) ? videoGateway.findAll() : videoGateway.findByName(name);
    }

}