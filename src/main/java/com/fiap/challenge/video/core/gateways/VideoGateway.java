package com.fiap.challenge.video.core.gateways;

import com.fiap.challenge.video.core.domain.Video;

import java.util.List;

public interface VideoGateway {

    Video save(Video video);

    Video findById(Long id);

    List<Video> findAll();

    List<Video> findByName(String name);

}
