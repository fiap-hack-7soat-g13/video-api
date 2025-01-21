package com.fiap.challenge.video.app.adapter.input.web.mapper;

import com.fiap.challenge.video.app.adapter.input.web.dto.VideoApiRequest;
import com.fiap.challenge.video.core.domain.Video;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-21T00:03:21-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Azul Systems, Inc.)"
)
@Component
public class VideoApiRequestMapperImpl implements VideoApiRequestMapper {

    @Override
    public Video toVideo(VideoApiRequest videoRequest) {
        if ( videoRequest == null ) {
            return null;
        }

        Video video = new Video();

        video.setName( videoRequest.getName() );

        return video;
    }
}
