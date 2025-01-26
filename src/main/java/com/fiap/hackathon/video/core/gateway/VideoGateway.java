package com.fiap.hackathon.video.core.gateway;

import com.fiap.hackathon.video.core.domain.Video;

import java.util.List;
import java.util.Optional;

public interface VideoGateway {

    Video save(Video video);

    Optional<Video> findById(Long id);

    List<Video> findAll();

}
