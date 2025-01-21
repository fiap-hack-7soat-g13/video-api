package com.fiap.challenge.video.app.adapter.input.web.mapper;

import com.fiap.challenge.video.app.adapter.input.web.dto.VideoApiResponse;
import com.fiap.challenge.video.core.domain.Video;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-21T00:03:20-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Azul Systems, Inc.)"
)
@Component
public class VideoApiResponseMapperImpl implements VideoApiResponseMapper {

    @Override
    public VideoApiResponse toVideoResponse(Video video) {
        if ( video == null ) {
            return null;
        }

        VideoApiResponse videoApiResponse = new VideoApiResponse();

        videoApiResponse.setName( video.getName() );

        return videoApiResponse;
    }

    @Override
    public List<VideoApiResponse> toVideo(List<Video> videos) {
        if ( videos == null ) {
            return null;
        }

        List<VideoApiResponse> list = new ArrayList<VideoApiResponse>( videos.size() );
        for ( Video video : videos ) {
            list.add( toVideoResponse( video ) );
        }

        return list;
    }
}
